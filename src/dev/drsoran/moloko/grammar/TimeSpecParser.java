// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-07-06 09:11:51

	package dev.drsoran.moloko.grammar;
	
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeSpecParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "HOURS", "DAYS", "MINUTES", "SECONDS", "NEVER", "MIDNIGHT", "MIDDAY", "NOON", "AT", "WS", "'.'", "':'", "'A'", "'M'", "'P'"
    };
    public static final int MIDDAY=11;
    public static final int SECONDS=8;
    public static final int INT=4;
    public static final int MIDNIGHT=10;
    public static final int EOF=-1;
    public static final int MINUTES=7;
    public static final int AT=13;
    public static final int T__19=19;
    public static final int DAYS=6;
    public static final int T__16=16;
    public static final int WS=14;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int NOON=12;
    public static final int NEVER=9;
    public static final int HOURS=5;

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



    // $ANTLR start "timespec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:62:1: timespec returns [long millis] : (v= INT (s= floatspec[ Integer.parseInt( $v.text ) ] | s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ] | s= naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= point_in_timespec ) ;
    public final long timespec() throws RecognitionException {
        long millis = 0;

        Token v=null;
        long s = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:63:2: ( (v= INT (s= floatspec[ Integer.parseInt( $v.text ) ] | s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ] | s= naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= point_in_timespec ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:63:3: (v= INT (s= floatspec[ Integer.parseInt( $v.text ) ] | s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ] | s= naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= point_in_timespec )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:63:3: (v= INT (s= floatspec[ Integer.parseInt( $v.text ) ] | s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ] | s= naturalspec[ Integer.parseInt( $v.text ) \t ] ) | s= point_in_timespec )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==INT) ) {
                alt2=1;
            }
            else if ( ((LA2_0>=NEVER && LA2_0<=AT)) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:63:6: v= INT (s= floatspec[ Integer.parseInt( $v.text ) ] | s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ] | s= naturalspec[ Integer.parseInt( $v.text ) \t ] )
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_timespec53); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:63:12: (s= floatspec[ Integer.parseInt( $v.text ) ] | s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ] | s= naturalspec[ Integer.parseInt( $v.text ) \t ] )
                    int alt1=3;
                    switch ( input.LA(1) ) {
                    case 15:
                        {
                        int LA1_1 = input.LA(2);

                        if ( (LA1_1==INT) ) {
                            int LA1_4 = input.LA(3);

                            if ( (LA1_4==HOURS) ) {
                                alt1=1;
                            }
                            else if ( (LA1_4==EOF||(LA1_4>=15 && LA1_4<=16)) ) {
                                alt1=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 16:
                        {
                        alt1=2;
                        }
                        break;
                    case HOURS:
                    case DAYS:
                    case MINUTES:
                    case SECONDS:
                        {
                        alt1=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 0, input);

                        throw nvae;
                    }

                    switch (alt1) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:63:16: s= floatspec[ Integer.parseInt( $v.text ) ]
                            {
                            pushFollow(FOLLOW_floatspec_in_timespec61);
                            s=floatspec(Integer.parseInt( (v!=null?v.getText():null) ));

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:64:10: s= separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t true /* only colon */ ]
                            {
                            pushFollow(FOLLOW_separatorspec_in_timespec78);
                            s=separatorspec(Integer.parseInt( (v!=null?v.getText():null) ), false /* value is not millis */, true  /* only colon          */);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:67:10: s= naturalspec[ Integer.parseInt( $v.text ) \t ]
                            {
                            pushFollow(FOLLOW_naturalspec_in_timespec93);
                            s=naturalspec(Integer.parseInt( (v!=null?v.getText():null) ));

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:68:8: s= point_in_timespec
                    {
                    pushFollow(FOLLOW_point_in_timespec_in_timespec110);
                    s=point_in_timespec();

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
    // $ANTLR end "timespec"


    // $ANTLR start "floatspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:74:1: floatspec[long value] returns [long millis] : '.' deciHour= INT HOURS ;
    public final long floatspec(long value) throws RecognitionException {
        long millis = 0;

        Token deciHour=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:75:2: ( '.' deciHour= INT HOURS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:75:4: '.' deciHour= INT HOURS
            {
            match(input,15,FOLLOW_15_in_floatspec131); 
            deciHour=(Token)match(input,INT,FOLLOW_INT_in_floatspec135); 
            match(input,HOURS,FOLLOW_HOURS_in_floatspec137); 

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
    // $ANTLR end "floatspec"


    // $ANTLR start "separatorspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:82:1: separatorspec[long value,\r\n\t\t\t\t boolean valIsMilli,\r\n\t\t\t\t boolean onlyColon] returns [long millis] : ({...}? ':' m= INT ( ':' s= INT )? | ( ':' | '.' ) m= INT ( ( ':' | '.' ) s= INT )? ) ;
    public final long separatorspec(long    value, boolean valIsMilli, boolean onlyColon) throws RecognitionException {
        long millis = 0;

        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:85:2: ( ({...}? ':' m= INT ( ':' s= INT )? | ( ':' | '.' ) m= INT ( ( ':' | '.' ) s= INT )? ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:85:4: ({...}? ':' m= INT ( ':' s= INT )? | ( ':' | '.' ) m= INT ( ( ':' | '.' ) s= INT )? )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:85:4: ({...}? ':' m= INT ( ':' s= INT )? | ( ':' | '.' ) m= INT ( ( ':' | '.' ) s= INT )? )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==16) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==INT) ) {
                    int LA5_3 = input.LA(3);

                    if ( ((onlyColon)) ) {
                        alt5=1;
                    }
                    else if ( (true) ) {
                        alt5=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA5_0==15) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:87:6: {...}? ':' m= INT ( ':' s= INT )?
                    {
                    if ( !((onlyColon)) ) {
                        throw new FailedPredicateException(input, "separatorspec", "$onlyColon");
                    }
                    match(input,16,FOLLOW_16_in_separatorspec172); 
                    m=(Token)match(input,INT,FOLLOW_INT_in_separatorspec176); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:88:9: ( ':' s= INT )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==16) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:88:10: ':' s= INT
                            {
                            match(input,16,FOLLOW_16_in_separatorspec189); 
                            s=(Token)match(input,INT,FOLLOW_INT_in_separatorspec193); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:90:11: ( ':' | '.' ) m= INT ( ( ':' | '.' ) s= INT )?
                    {
                    if ( (input.LA(1)>=15 && input.LA(1)<=16) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    m=(Token)match(input,INT,FOLLOW_INT_in_separatorspec222); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:91:9: ( ( ':' | '.' ) s= INT )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=15 && LA4_0<=16)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:91:10: ( ':' | '.' ) s= INT
                            {
                            if ( (input.LA(1)>=15 && input.LA(1)<=16) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            s=(Token)match(input,INT,FOLLOW_INT_in_separatorspec243); 
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
    // $ANTLR end "separatorspec"


    // $ANTLR start "naturalspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:101:1: naturalspec[long value] returns [long millis] : ( DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? | HOURS (m= INT MINUTES )? (s= INT SECONDS )? | MINUTES (s= INT SECONDS )? | SECONDS );
    public final long naturalspec(long value) throws RecognitionException {
        long millis = 0;

        Token h=null;
        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:102:2: ( DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? | HOURS (m= INT MINUTES )? (s= INT SECONDS )? | MINUTES (s= INT SECONDS )? | SECONDS )
            int alt12=4;
            switch ( input.LA(1) ) {
            case DAYS:
                {
                alt12=1;
                }
                break;
            case HOURS:
                {
                alt12=2;
                }
                break;
            case MINUTES:
                {
                alt12=3;
                }
                break;
            case SECONDS:
                {
                alt12=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:102:4: DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_naturalspec273); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:102:10: (h= INT HOURS )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==INT) ) {
                        int LA6_1 = input.LA(2);

                        if ( (LA6_1==HOURS) ) {
                            alt6=1;
                        }
                    }
                    switch (alt6) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:102:11: h= INT HOURS
                            {
                            h=(Token)match(input,INT,FOLLOW_INT_in_naturalspec279); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   
                            match(input,HOURS,FOLLOW_HOURS_in_naturalspec283); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:103:6: (m= INT MINUTES )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==INT) ) {
                        int LA7_1 = input.LA(2);

                        if ( (LA7_1==MINUTES) ) {
                            alt7=1;
                        }
                    }
                    switch (alt7) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:103:7: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_naturalspec297); 
                             millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_naturalspec301); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:104:6: (s= INT SECONDS )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==INT) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:104:7: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_naturalspec313); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_naturalspec317); 

                            }
                            break;

                    }


                    		millis = value * DAY_MILLIS;
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:108:4: HOURS (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    match(input,HOURS,FOLLOW_HOURS_in_naturalspec327); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:108:10: (m= INT MINUTES )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==INT) ) {
                        int LA9_1 = input.LA(2);

                        if ( (LA9_1==MINUTES) ) {
                            alt9=1;
                        }
                    }
                    switch (alt9) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:108:11: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_naturalspec332); 
                             millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_naturalspec336); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:109:6: (s= INT SECONDS )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==INT) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:109:7: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_naturalspec348); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_naturalspec352); 

                            }
                            break;

                    }


                    		millis += value * HOUR_MILLIS;
                    	

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:113:4: MINUTES (s= INT SECONDS )?
                    {
                    match(input,MINUTES,FOLLOW_MINUTES_in_naturalspec362); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:113:12: (s= INT SECONDS )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==INT) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:113:13: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_naturalspec367); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_naturalspec371); 

                            }
                            break;

                    }


                    		millis += value * MINUTE_MILLIS;
                    	

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:117:4: SECONDS
                    {
                    match(input,SECONDS,FOLLOW_SECONDS_in_naturalspec381); 

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
    // $ANTLR end "naturalspec"


    // $ANTLR start "point_in_timespec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:123:1: point_in_timespec returns [long millis] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | AT v= INT (s= separatorspec[$millis,\r\n\t \t\t\t\t\t\t true /*value is millis */,\r\n\t \t\t\t\t\t\t false /*colon and dot*/ ] )? (p= am_pm_spec )? );
    public final long point_in_timespec() throws RecognitionException {
        long millis = 0;

        Token v=null;
        long s = 0;

        long p = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:124:2: ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | AT v= INT (s= separatorspec[$millis,\r\n\t \t\t\t\t\t\t true /*value is millis */,\r\n\t \t\t\t\t\t\t false /*colon and dot*/ ] )? (p= am_pm_spec )? )
            int alt15=4;
            switch ( input.LA(1) ) {
            case NEVER:
                {
                alt15=1;
                }
                break;
            case MIDNIGHT:
                {
                alt15=2;
                }
                break;
            case MIDDAY:
            case NOON:
                {
                alt15=3;
                }
                break;
            case AT:
                {
                alt15=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:124:4: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_point_in_timespec399); 

                    		millis = -1;		
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:128:4: MIDNIGHT
                    {
                    match(input,MIDNIGHT,FOLLOW_MIDNIGHT_in_point_in_timespec407); 

                    		final Calendar c = Calendar.getInstance();
                          c.setTimeInMillis( System.currentTimeMillis() );
                          c.set( Calendar.HOUR_OF_DAY, 23 );
                          c.set( Calendar.MINUTE, 59 );
                          c.set( Calendar.SECOND, 59 );
                          
                          millis = c.getTime().getTime();
                    	

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:138:4: ( MIDDAY | NOON )
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
                          c.setTimeInMillis( System.currentTimeMillis() );
                          c.set( Calendar.HOUR_OF_DAY, 12 );
                          c.set( Calendar.MINUTE, 00 );
                          c.set( Calendar.SECOND, 00 );
                          
                          millis = c.getTime().getTime();
                    	

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:148:4: AT v= INT (s= separatorspec[$millis,\r\n\t \t\t\t\t\t\t true /*value is millis */,\r\n\t \t\t\t\t\t\t false /*colon and dot*/ ] )? (p= am_pm_spec )?
                    {
                    match(input,AT,FOLLOW_AT_in_point_in_timespec431); 
                    v=(Token)match(input,INT,FOLLOW_INT_in_point_in_timespec435); 

                    		  		millis = parsePointInTime( (v!=null?v.getText():null) );
                    	  		
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:152:4: (s= separatorspec[$millis,\r\n\t \t\t\t\t\t\t true /*value is millis */,\r\n\t \t\t\t\t\t\t false /*colon and dot*/ ] )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( ((LA13_0>=15 && LA13_0<=16)) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:152:6: s= separatorspec[$millis,\r\n\t \t\t\t\t\t\t true /*value is millis */,\r\n\t \t\t\t\t\t\t false /*colon and dot*/ ]
                            {
                            pushFollow(FOLLOW_separatorspec_in_point_in_timespec451);
                            s=separatorspec(millis, true  /*value is millis */, false /*colon and dot*/);

                            state._fsp--;


                            	  		   millis = s;
                            	  		

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:158:4: (p= am_pm_spec )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==17||LA14_0==19) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:158:6: p= am_pm_spec
                            {
                            pushFollow(FOLLOW_am_pm_spec_in_point_in_timespec473);
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
    // $ANTLR end "point_in_timespec"


    // $ANTLR start "am_pm_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:164:1: am_pm_spec returns [long millis] : ( 'A' ( 'M' )? | 'P' ( 'M' )? );
    public final long am_pm_spec() throws RecognitionException {
        long millis = 0;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:166:2: ( 'A' ( 'M' )? | 'P' ( 'M' )? )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==17) ) {
                alt18=1;
            }
            else if ( (LA18_0==19) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:166:4: 'A' ( 'M' )?
                    {
                    match(input,17,FOLLOW_17_in_am_pm_spec500); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:166:7: ( 'M' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==18) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:166:8: 'M'
                            {
                            match(input,18,FOLLOW_18_in_am_pm_spec502); 

                            }
                            break;

                    }


                    		millis = 0;
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:170:4: 'P' ( 'M' )?
                    {
                    match(input,19,FOLLOW_19_in_am_pm_spec512); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:170:7: ( 'M' )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==18) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:170:8: 'M'
                            {
                            match(input,18,FOLLOW_18_in_am_pm_spec514); 

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


 

    public static final BitSet FOLLOW_INT_in_timespec53 = new BitSet(new long[]{0x00000000000181E0L});
    public static final BitSet FOLLOW_floatspec_in_timespec61 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_separatorspec_in_timespec78 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_naturalspec_in_timespec93 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_point_in_timespec_in_timespec110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_floatspec131 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_floatspec135 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_HOURS_in_floatspec137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_separatorspec172 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_separatorspec176 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_16_in_separatorspec189 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_separatorspec193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_separatorspec214 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_separatorspec222 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_set_in_separatorspec235 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_separatorspec243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_naturalspec273 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_naturalspec279 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_HOURS_in_naturalspec283 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_naturalspec297 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_MINUTES_in_naturalspec301 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_naturalspec313 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SECONDS_in_naturalspec317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HOURS_in_naturalspec327 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_naturalspec332 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_MINUTES_in_naturalspec336 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_naturalspec348 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SECONDS_in_naturalspec352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUTES_in_naturalspec362 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_naturalspec367 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SECONDS_in_naturalspec371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SECONDS_in_naturalspec381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_point_in_timespec399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDNIGHT_in_point_in_timespec407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_point_in_timespec415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_point_in_timespec431 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_point_in_timespec435 = new BitSet(new long[]{0x00000000000B8002L});
    public static final BitSet FOLLOW_separatorspec_in_point_in_timespec451 = new BitSet(new long[]{0x00000000000A0002L});
    public static final BitSet FOLLOW_am_pm_spec_in_point_in_timespec473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_am_pm_spec500 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_18_in_am_pm_spec502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_am_pm_spec512 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_18_in_am_pm_spec514 = new BitSet(new long[]{0x0000000000000002L});

}