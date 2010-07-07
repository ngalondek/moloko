// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-07-07 15:24:11

	package dev.drsoran.moloko.grammar;
	
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;
	import java.util.Locale;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeSpecParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "INT", "DOT", "COLON", "MINUS", "DATE_SEP", "ON", "STs", "OF", "MONTHS", "HOURS", "DAYS", "MINUTES", "SECONDS", "NEVER", "MIDNIGHT", "MIDDAY", "NOON", "IN", "WS", "'-a'", "','", "'A'", "'M'", "'P'"
    };
    public static final int STs=11;
    public static final int MIDDAY=20;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int ON=10;
    public static final int SECONDS=17;
    public static final int INT=5;
    public static final int MIDNIGHT=19;
    public static final int MINUS=8;
    public static final int EOF=-1;
    public static final int OF=12;
    public static final int MINUTES=16;
    public static final int AT=4;
    public static final int COLON=7;
    public static final int DAYS=15;
    public static final int WS=23;
    public static final int IN=22;
    public static final int MONTHS=13;
    public static final int NOON=21;
    public static final int NEVER=18;
    public static final int DATE_SEP=9;
    public static final int DOT=6;
    public static final int HOURS=14;

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
    			
    		
    	private final long parseFullDate( String  day,
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



    // $ANTLR start "parseDateTime"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:90:1: parseDateTime returns [long millis] : (d= date_spec ( ( AT )? t= time_point_in_timespec[ $millis ] )? | t= time_spec );
    public final long parseDateTime() throws RecognitionException {
        long millis = 0;

        long d = 0;

        long t = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:91:2: (d= date_spec ( ( AT )? t= time_point_in_timespec[ $millis ] )? | t= time_spec )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:91:4: d= date_spec ( ( AT )? t= time_point_in_timespec[ $millis ] )?
                    {
                    pushFollow(FOLLOW_date_spec_in_parseDateTime51);
                    d=date_spec();

                    state._fsp--;


                    	    	millis = d;
                    	  	 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:95:3: ( ( AT )? t= time_point_in_timespec[ $millis ] )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( ((LA2_0>=AT && LA2_0<=INT)||(LA2_0>=NEVER && LA2_0<=NOON)) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:95:5: ( AT )? t= time_point_in_timespec[ $millis ]
                            {
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:95:5: ( AT )?
                            int alt1=2;
                            int LA1_0 = input.LA(1);

                            if ( (LA1_0==AT) ) {
                                alt1=1;
                            }
                            switch (alt1) {
                                case 1 :
                                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:95:5: AT
                                    {
                                    match(input,AT,FOLLOW_AT_in_parseDateTime62); 

                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_time_point_in_timespec_in_parseDateTime67);
                            t=time_point_in_timespec(millis);

                            state._fsp--;


                            	  	   if ( t != -1 ) millis = t;
                            	  	 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:99:4: t= time_spec
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:105:1: date_spec returns [long millis] : (d= date_full[ true ] | d= date_on_Xst_of ) ;
    public final long date_spec() throws RecognitionException {
        long millis = 0;

        long d = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:106:2: ( (d= date_full[ true ] | d= date_on_Xst_of ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:106:4: (d= date_full[ true ] | d= date_on_Xst_of )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:106:4: (d= date_full[ true ] | d= date_on_Xst_of )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==INT) ) {
                switch ( input.LA(2) ) {
                case DOT:
                case MINUS:
                    {
                    int LA4_3 = input.LA(3);

                    if ( (LA4_3==INT) ) {
                        alt4=1;
                    }
                    else if ( (LA4_3==MONTHS) ) {
                        alt4=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case STs:
                case OF:
                case MONTHS:
                case 24:
                case 25:
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
            else if ( (LA4_0==ON) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:106:8: d= date_full[ true ]
                    {
                    pushFollow(FOLLOW_date_full_in_date_spec111);
                    d=date_full(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:107:6: d= date_on_Xst_of
                    {
                    pushFollow(FOLLOW_date_on_Xst_of_in_date_spec121);
                    d=date_on_Xst_of();

                    state._fsp--;


                    }
                    break;

            }


            		millis = d;
            	

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:113:1: date_full[boolean dayFirst] returns [long millis] : ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT ) ;
    public final long date_full(boolean dayFirst) throws RecognitionException {
        long millis = 0;

        Token pt1=null;
        Token pt2=null;
        Token pt3=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:114:2: ( ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:114:4: ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:114:4: ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:114:6: {...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT
                    {
                    if ( !((dayFirst)) ) {
                        throw new FailedPredicateException(input, "date_full", "$dayFirst");
                    }
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full149); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full181); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full213); 

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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:137:12: pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT
                    {
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full247); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full281); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full315); 

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


    // $ANTLR start "date_on_Xst_of"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:162:1: date_on_Xst_of returns [long millis] : ( ON )? d= INT ( STs )? ( OF | '-a' | MINUS | ',' | DOT )? m= MONTHS ( MINUS | DOT )? (y= INT )? ;
    public final long date_on_Xst_of() throws RecognitionException {
        long millis = 0;

        Token d=null;
        Token m=null;
        Token y=null;


        		String year = null;
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:2: ( ( ON )? d= INT ( STs )? ( OF | '-a' | MINUS | ',' | DOT )? m= MONTHS ( MINUS | DOT )? (y= INT )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:4: ( ON )? d= INT ( STs )? ( OF | '-a' | MINUS | ',' | DOT )? m= MONTHS ( MINUS | DOT )? (y= INT )?
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:4: ( ON )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ON) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:4: ON
                    {
                    match(input,ON,FOLLOW_ON_in_date_on_Xst_of357); 

                    }
                    break;

            }

            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of362); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:14: ( STs )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==STs) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:14: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_Xst_of364); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:19: ( OF | '-a' | MINUS | ',' | DOT )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==DOT||LA8_0==MINUS||LA8_0==OF||(LA8_0>=24 && LA8_0<=25)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( input.LA(1)==DOT||input.LA(1)==MINUS||input.LA(1)==OF||(input.LA(1)>=24 && input.LA(1)<=25) ) {
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

            m=(Token)match(input,MONTHS,FOLLOW_MONTHS_in_date_on_Xst_of390); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:61: ( MINUS | DOT )?
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:76: (y= INT )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==INT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:167:77: y= INT
                    {
                    y=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of404); 
                     year = (y!=null?y.getText():null); 

                    }
                    break;

            }


            		if ( year == null )
            			year = new SimpleDateFormat( "yyyy" ).format( new Date() );
            			
            		millis = parseFullDate( (d!=null?d.getText():null), (m!=null?m.getText():null), year, true );
            	

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
    // $ANTLR end "date_on_Xst_of"


    // $ANTLR start "time_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:176:1: time_spec returns [long millis] : ( AT )? (v= INT (s= time_floatspec[ Integer.parseInt( $v.text ) ] | s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | s= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= time_point_in_timespec[ -1 ] ) ;
    public final long time_spec() throws RecognitionException {
        long millis = 0;

        Token v=null;
        long s = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:177:2: ( ( AT )? (v= INT (s= time_floatspec[ Integer.parseInt( $v.text ) ] | s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | s= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= time_point_in_timespec[ -1 ] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:177:4: ( AT )? (v= INT (s= time_floatspec[ Integer.parseInt( $v.text ) ] | s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | s= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= time_point_in_timespec[ -1 ] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:177:4: ( AT )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==AT) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:177:4: AT
                    {
                    match(input,AT,FOLLOW_AT_in_time_spec426); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:178:3: (v= INT (s= time_floatspec[ Integer.parseInt( $v.text ) ] | s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | s= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= time_point_in_timespec[ -1 ] )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==INT) ) {
                switch ( input.LA(2) ) {
                case COLON:
                    {
                    int LA13_3 = input.LA(3);

                    if ( (LA13_3==INT) ) {
                        alt13=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case DOT:
                    {
                    int LA13_4 = input.LA(3);

                    if ( (LA13_4==INT) ) {
                        alt13=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case 26:
                case 28:
                    {
                    alt13=2;
                    }
                    break;
                case HOURS:
                case DAYS:
                case MINUTES:
                case SECONDS:
                    {
                    alt13=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }

            }
            else if ( ((LA13_0>=NEVER && LA13_0<=NOON)) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:178:6: v= INT (s= time_floatspec[ Integer.parseInt( $v.text ) ] | s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | s= time_naturalspec[ Integer.parseInt( $v.text ) \t ] )
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_spec436); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:178:12: (s= time_floatspec[ Integer.parseInt( $v.text ) ] | s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | s= time_naturalspec[ Integer.parseInt( $v.text ) \t ] )
                    int alt12=3;
                    switch ( input.LA(1) ) {
                    case DOT:
                        {
                        int LA12_1 = input.LA(2);

                        if ( (LA12_1==INT) ) {
                            int LA12_4 = input.LA(3);

                            if ( (LA12_4==HOURS) ) {
                                alt12=1;
                            }
                            else if ( (LA12_4==EOF||(LA12_4>=DOT && LA12_4<=COLON)) ) {
                                alt12=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 12, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 12, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case COLON:
                        {
                        alt12=2;
                        }
                        break;
                    case HOURS:
                    case DAYS:
                    case MINUTES:
                    case SECONDS:
                        {
                        alt12=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }

                    switch (alt12) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:178:16: s= time_floatspec[ Integer.parseInt( $v.text ) ]
                            {
                            pushFollow(FOLLOW_time_floatspec_in_time_spec444);
                            s=time_floatspec(Integer.parseInt( (v!=null?v.getText():null) ));

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:179:10: s= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_spec463);
                            s=time_separatorspec(Integer.parseInt( (v!=null?v.getText():null) ), false /* value is not millis */, true  /* only colon          */);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:182:10: s= time_naturalspec[ Integer.parseInt( $v.text ) \t ]
                            {
                            pushFollow(FOLLOW_time_naturalspec_in_time_spec478);
                            s=time_naturalspec(Integer.parseInt( (v!=null?v.getText():null) ));

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:183:8: s= time_point_in_timespec[ -1 ]
                    {
                    pushFollow(FOLLOW_time_point_in_timespec_in_time_spec495);
                    s=time_point_in_timespec(-1);

                    state._fsp--;


                    }
                    break;

            }


            		millis = s;
            	

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
    // $ANTLR end "time_spec"


    // $ANTLR start "time_floatspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:189:1: time_floatspec[long value] returns [long millis] : '.' deciHour= INT HOURS ;
    public final long time_floatspec(long value) throws RecognitionException {
        long millis = 0;

        Token deciHour=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:190:2: ( '.' deciHour= INT HOURS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:190:4: '.' deciHour= INT HOURS
            {
            match(input,DOT,FOLLOW_DOT_in_time_floatspec519); 
            deciHour=(Token)match(input,INT,FOLLOW_INT_in_time_floatspec523); 
            match(input,HOURS,FOLLOW_HOURS_in_time_floatspec525); 

            		final float deciHourVal = (float)Integer.parseInt( (deciHour!=null?deciHour.getText():null) ) * 0.1f;
            		millis = value * HOUR_MILLIS + (long)(deciHourVal * HOUR_MILLIS);
            	

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
    // $ANTLR end "time_floatspec"


    // $ANTLR start "time_separatorspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:197:1: time_separatorspec[long value,\r\n\t\t\t\t \t\t boolean valIsMilli,\r\n\t\t\t\t\t\t boolean onlyColon] returns [long millis] : ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ) ;
    public final long time_separatorspec(long    value, boolean valIsMilli, boolean onlyColon) throws RecognitionException {
        long millis = 0;

        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:200:2: ( ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:200:4: ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:200:4: ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==COLON) ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==INT) ) {
                    int LA16_3 = input.LA(3);

                    if ( ((onlyColon)) ) {
                        alt16=1;
                    }
                    else if ( (true) ) {
                        alt16=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA16_0==DOT) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:202:6: {...}? COLON m= INT ( COLON s= INT )?
                    {
                    if ( !((onlyColon)) ) {
                        throw new FailedPredicateException(input, "time_separatorspec", "$onlyColon");
                    }
                    match(input,COLON,FOLLOW_COLON_in_time_separatorspec562); 
                    m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec566); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:203:9: ( COLON s= INT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==COLON) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:203:10: COLON s= INT
                            {
                            match(input,COLON,FOLLOW_COLON_in_time_separatorspec579); 
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec583); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:205:11: ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )?
                    {
                    if ( (input.LA(1)>=DOT && input.LA(1)<=COLON) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec612); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:206:9: ( ( COLON | DOT ) s= INT )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>=DOT && LA15_0<=COLON)) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:206:10: ( COLON | DOT ) s= INT
                            {
                            if ( (input.LA(1)>=DOT && input.LA(1)<=COLON) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec633); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                            }
                            break;

                    }


                    }
                    break;

            }


            	  	  if ( valIsMilli )
              			  millis += value;
            	  	  else
            			  millis += value * HOUR_MILLIS;			  
            	  

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
    // $ANTLR end "time_separatorspec"


    // $ANTLR start "time_naturalspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:216:1: time_naturalspec[long value] returns [long millis] : ( DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? | HOURS (m= INT MINUTES )? (s= INT SECONDS )? | MINUTES (s= INT SECONDS )? | SECONDS );
    public final long time_naturalspec(long value) throws RecognitionException {
        long millis = 0;

        Token h=null;
        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:217:2: ( DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? | HOURS (m= INT MINUTES )? (s= INT SECONDS )? | MINUTES (s= INT SECONDS )? | SECONDS )
            int alt23=4;
            switch ( input.LA(1) ) {
            case DAYS:
                {
                alt23=1;
                }
                break;
            case HOURS:
                {
                alt23=2;
                }
                break;
            case MINUTES:
                {
                alt23=3;
                }
                break;
            case SECONDS:
                {
                alt23=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:217:4: DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_time_naturalspec665); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:217:10: (h= INT HOURS )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==INT) ) {
                        int LA17_1 = input.LA(2);

                        if ( (LA17_1==HOURS) ) {
                            alt17=1;
                        }
                    }
                    switch (alt17) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:217:11: h= INT HOURS
                            {
                            h=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec671); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   
                            match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec675); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:218:6: (m= INT MINUTES )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==INT) ) {
                        int LA18_1 = input.LA(2);

                        if ( (LA18_1==MINUTES) ) {
                            alt18=1;
                        }
                    }
                    switch (alt18) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:218:7: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec689); 
                             millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec693); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:219:6: (s= INT SECONDS )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==INT) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:219:7: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec705); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec709); 

                            }
                            break;

                    }


                    		millis = value * DAY_MILLIS;
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:223:4: HOURS (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec719); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:223:10: (m= INT MINUTES )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==INT) ) {
                        int LA20_1 = input.LA(2);

                        if ( (LA20_1==MINUTES) ) {
                            alt20=1;
                        }
                    }
                    switch (alt20) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:223:11: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec724); 
                             millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec728); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:224:6: (s= INT SECONDS )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==INT) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:224:7: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec740); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec744); 

                            }
                            break;

                    }


                    		millis += value * HOUR_MILLIS;
                    	

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:228:4: MINUTES (s= INT SECONDS )?
                    {
                    match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec754); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:228:12: (s= INT SECONDS )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==INT) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:228:13: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec759); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec763); 

                            }
                            break;

                    }


                    		millis += value * MINUTE_MILLIS;
                    	

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:232:4: SECONDS
                    {
                    match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec773); 

                    		millis += value * SECOND_MILLIS;
                    	

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
    // $ANTLR end "time_naturalspec"


    // $ANTLR start "time_point_in_timespec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:238:1: time_point_in_timespec[long date] returns [long millis] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | v= INT (s= time_separatorspec[ $millis,\r\n\t \t\t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )? );
    public final long time_point_in_timespec(long date) throws RecognitionException {
        long millis = 0;

        Token v=null;
        long s = 0;

        long p = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:239:2: ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | v= INT (s= time_separatorspec[ $millis,\r\n\t \t\t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )? )
            int alt26=4;
            switch ( input.LA(1) ) {
            case NEVER:
                {
                alt26=1;
                }
                break;
            case MIDNIGHT:
                {
                alt26=2;
                }
                break;
            case MIDDAY:
            case NOON:
                {
                alt26=3;
                }
                break;
            case INT:
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:239:4: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_time_point_in_timespec794); 

                    		millis = -1;		
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:243:4: MIDNIGHT
                    {
                    match(input,MIDNIGHT,FOLLOW_MIDNIGHT_in_time_point_in_timespec802); 

                    		final Calendar c = Calendar.getInstance();
                          c.setTimeInMillis( ( date == -1 ) ? System.currentTimeMillis() : date );
                          c.set( Calendar.HOUR_OF_DAY, 23 );
                          c.set( Calendar.MINUTE, 59 );
                          c.set( Calendar.SECOND, 59 );
                          
                          millis = c.getTime().getTime();
                    	

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:253:4: ( MIDDAY | NOON )
                    {
                    if ( (input.LA(1)>=MIDDAY && input.LA(1)<=NOON) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    		final Calendar c = Calendar.getInstance();
                          c.setTimeInMillis( ( date == -1 ) ? System.currentTimeMillis() : date );
                          c.set( Calendar.HOUR_OF_DAY, 12 );
                          c.set( Calendar.MINUTE, 00 );
                          c.set( Calendar.SECOND, 00 );
                          
                          millis = c.getTime().getTime();
                    	

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:263:6: v= INT (s= time_separatorspec[ $millis,\r\n\t \t\t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )?
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_point_in_timespec830); 

                    		  		if ( date == -1 )
                    		  			millis = parsePointInTime( (v!=null?v.getText():null) );
                    		  		else
                    		  			millis = date + parsePointInTime( (v!=null?v.getText():null) );
                    	  		
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:270:4: (s= time_separatorspec[ $millis,\r\n\t \t\t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( ((LA24_0>=DOT && LA24_0<=COLON)) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:270:6: s= time_separatorspec[ $millis,\r\n\t \t\t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_point_in_timespec846);
                            s=time_separatorspec(millis, true  /* value is millis */, false /* colon and dot   */);

                            state._fsp--;


                            	  		   millis = s;
                            	  		

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:276:4: (p= am_pm_spec )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==26||LA25_0==28) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:276:6: p= am_pm_spec
                            {
                            pushFollow(FOLLOW_am_pm_spec_in_time_point_in_timespec868);
                            p=am_pm_spec();

                            state._fsp--;


                            	  		   millis += p;
                            	  		

                            }
                            break;

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
        return millis;
    }
    // $ANTLR end "time_point_in_timespec"


    // $ANTLR start "am_pm_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:282:1: am_pm_spec returns [long millis] : ( 'A' ( 'M' )? | 'P' ( 'M' )? );
    public final long am_pm_spec() throws RecognitionException {
        long millis = 0;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:284:2: ( 'A' ( 'M' )? | 'P' ( 'M' )? )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==26) ) {
                alt29=1;
            }
            else if ( (LA29_0==28) ) {
                alt29=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:284:4: 'A' ( 'M' )?
                    {
                    match(input,26,FOLLOW_26_in_am_pm_spec896); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:284:7: ( 'M' )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==27) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:284:8: 'M'
                            {
                            match(input,27,FOLLOW_27_in_am_pm_spec898); 

                            }
                            break;

                    }


                    		millis = 0;
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:288:4: 'P' ( 'M' )?
                    {
                    match(input,28,FOLLOW_28_in_am_pm_spec908); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:288:7: ( 'M' )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==27) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:288:8: 'M'
                            {
                            match(input,27,FOLLOW_27_in_am_pm_spec910); 

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
        "\14\uffff";
    static final String DFA3_eofS =
        "\1\uffff\1\3\4\uffff\2\3\4\uffff";
    static final String DFA3_minS =
        "\1\4\1\6\2\uffff\2\5\2\6\2\5\2\uffff";
    static final String DFA3_maxS =
        "\1\25\1\34\2\uffff\1\15\1\5\2\34\2\5\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\1\1\2\6\uffff\2\1";
    static final String DFA3_specialS =
        "\14\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\3\1\1\4\uffff\1\2\7\uffff\4\3",
            "\1\4\1\5\2\2\1\uffff\3\2\4\3\6\uffff\2\2\1\3\1\uffff\1\3",
            "",
            "",
            "\1\6\7\uffff\1\2",
            "\1\7",
            "\2\10\2\2\4\uffff\1\3\13\uffff\1\3\1\uffff\1\3",
            "\1\10\1\11\2\2\20\uffff\1\3\1\uffff\1\3",
            "\1\12",
            "\1\13",
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
            return "90:1: parseDateTime returns [long millis] : (d= date_spec ( ( AT )? t= time_point_in_timespec[ $millis ] )? | t= time_spec );";
        }
    }
 

    public static final BitSet FOLLOW_date_spec_in_parseDateTime51 = new BitSet(new long[]{0x00000000003C0032L});
    public static final BitSet FOLLOW_AT_in_parseDateTime62 = new BitSet(new long[]{0x00000000003C0030L});
    public static final BitSet FOLLOW_time_point_in_timespec_in_parseDateTime67 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_spec_in_parseDateTime85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_full_in_date_spec111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_Xst_of_in_date_spec121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full149 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full151 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full181 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full183 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full247 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full249 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full281 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full283 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_date_on_Xst_of357 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of362 = new BitSet(new long[]{0x0000000003003940L});
    public static final BitSet FOLLOW_STs_in_date_on_Xst_of364 = new BitSet(new long[]{0x0000000003003140L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of367 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_MONTHS_in_date_on_Xst_of390 = new BitSet(new long[]{0x0000000000000162L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of392 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_time_spec426 = new BitSet(new long[]{0x00000000003C0030L});
    public static final BitSet FOLLOW_INT_in_time_spec436 = new BitSet(new long[]{0x000000000003C0C0L});
    public static final BitSet FOLLOW_time_floatspec_in_time_spec444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_spec463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_in_time_spec478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_point_in_timespec_in_time_spec495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_time_floatspec519 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_floatspec523 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_HOURS_in_time_floatspec525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec562 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec566 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec579 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_separatorspec604 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec612 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_set_in_time_separatorspec625 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_time_naturalspec665 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec671 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec675 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec689 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec693 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec705 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec719 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec724 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec728 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec740 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec754 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec759 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_time_point_in_timespec794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_timespec802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_point_in_timespec810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_point_in_timespec830 = new BitSet(new long[]{0x00000000140000C2L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_point_in_timespec846 = new BitSet(new long[]{0x0000000014000002L});
    public static final BitSet FOLLOW_am_pm_spec_in_time_point_in_timespec868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_am_pm_spec896 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_27_in_am_pm_spec898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_am_pm_spec908 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_27_in_am_pm_spec910 = new BitSet(new long[]{0x0000000000000002L});

}