// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g 2010-07-05 08:27:23

	package dev.drsoran.moloko.grammar;
	
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DateTimeParserParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "COLON", "FLOAT", "HOURS", "MINUTES", "SECONDS", "DATE_SEP", "DOT", "NUMBER", "NOW", "TODAY", "TOMORROW", "TONIGHT", "YESTERDAY", "WS"
    };
    public static final int MINUTES=8;
    public static final int COLON=5;
    public static final int TODAY=14;
    public static final int WS=18;
    public static final int TONIGHT=16;
    public static final int NUMBER=12;
    public static final int NOW=13;
    public static final int SECONDS=9;
    public static final int INT=4;
    public static final int FLOAT=6;
    public static final int DATE_SEP=10;
    public static final int DOT=11;
    public static final int EOF=-1;
    public static final int YESTERDAY=17;
    public static final int HOURS=7;
    public static final int TOMORROW=15;

    // delegates
    // delegators


        public DateTimeParserParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public DateTimeParserParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return DateTimeParserParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g"; }


    	private final static long SECOND_MILLIS = 1000;
    		
    	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    	
    	private final static long HOUR_MILLIS   = 60 * MINUTE_MILLIS;
    	
    	private final static long DAY_MILLIS    = 24 * HOUR_MILLIS;
    	
    	private final static SimpleDateFormat FULL_DATE_PARSER = new SimpleDateFormat( "dd.MM.yyyy" );
    		
    		
    	private final long parseFullDate( String day, String month, String year )
    	{
    		long millis = -1;
    					
    		try
    		{		
    			millis = FULL_DATE_PARSER.parse( day + "." + month + "." + year ).getTime();			
    		}
    		catch( ParseException e )
    		{
    			millis = -1;
    		}
    		
    		return millis;
    	}



    // $ANTLR start "doNotCall"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:52:1: doNotCall : ;
    public final void doNotCall() throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:52:11: ()
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:52:13: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "doNotCall"


    // $ANTLR start "timespec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:54:1: timespec returns [long millis] : (h= INT COLON m= INT ( COLON s= INT )? | ( (h= INT | h= FLOAT ) HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? );
    public final long timespec() throws RecognitionException {
        long millis = 0;

        Token h=null;
        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:55:2: (h= INT COLON m= INT ( COLON s= INT )? | ( (h= INT | h= FLOAT ) HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INT) ) {
                int LA6_1 = input.LA(2);

                if ( ((LA6_1>=HOURS && LA6_1<=SECONDS)) ) {
                    alt6=2;
                }
                else if ( (LA6_1==COLON) ) {
                    alt6=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA6_0==EOF||LA6_0==FLOAT) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:55:11: h= INT COLON m= INT ( COLON s= INT )?
                    {
                    h=(Token)match(input,INT,FOLLOW_INT_in_timespec63); 
                     millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   
                    match(input,COLON,FOLLOW_COLON_in_timespec73); 
                    m=(Token)match(input,INT,FOLLOW_INT_in_timespec77); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:57:4: ( COLON s= INT )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==COLON) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:57:5: COLON s= INT
                            {
                            match(input,COLON,FOLLOW_COLON_in_timespec87); 
                            s=(Token)match(input,INT,FOLLOW_INT_in_timespec91); 

                            }
                            break;

                    }

                     millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:59:4: ( (h= INT | h= FLOAT ) HOURS )? (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:59:4: ( (h= INT | h= FLOAT ) HOURS )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==INT) ) {
                        int LA3_1 = input.LA(2);

                        if ( (LA3_1==HOURS) ) {
                            alt3=1;
                        }
                    }
                    else if ( (LA3_0==FLOAT) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:59:6: (h= INT | h= FLOAT ) HOURS
                            {
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:59:6: (h= INT | h= FLOAT )
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( (LA2_0==INT) ) {
                                alt2=1;
                            }
                            else if ( (LA2_0==FLOAT) ) {
                                alt2=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 0, input);

                                throw nvae;
                            }
                            switch (alt2) {
                                case 1 :
                                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:59:10: h= INT
                                    {
                                    h=(Token)match(input,INT,FOLLOW_INT_in_timespec111); 
                                     millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   

                                    }
                                    break;
                                case 2 :
                                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:60:8: h= FLOAT
                                    {
                                    h=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_timespec125); 
                                     millis += Float.parseFloat( (h!=null?h.getText():null) ) * HOUR_MILLIS;   

                                    }
                                    break;

                            }

                            match(input,HOURS,FOLLOW_HOURS_in_timespec131); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:61:6: (m= INT MINUTES )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==INT) ) {
                        int LA4_1 = input.LA(2);

                        if ( (LA4_1==MINUTES) ) {
                            alt4=1;
                        }
                    }
                    switch (alt4) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:61:10: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_timespec149); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_timespec157); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:62:6: (s= INT SECONDS )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==INT) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:62:10: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_timespec173); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_timespec181); 

                            }
                            break;

                    }


                    }
                    break;

            }
        }
        catch (NumberFormatException nfe) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "timespec"


    // $ANTLR start "fullDate"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:69:1: fullDate[boolean dayFirst] returns [long millis] : ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT ) ;
    public final long fullDate(boolean dayFirst) throws RecognitionException {
        long millis = 0;

        Token pt1=null;
        Token pt2=null;
        Token pt3=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:70:2: ( ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:70:4: ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:70:4: ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==INT) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==DATE_SEP) ) {
                    int LA7_2 = input.LA(3);

                    if ( (LA7_2==INT) ) {
                        int LA7_3 = input.LA(4);

                        if ( (LA7_3==DATE_SEP) ) {
                            int LA7_4 = input.LA(5);

                            if ( (LA7_4==INT) ) {
                                int LA7_5 = input.LA(6);

                                if ( ((dayFirst)) ) {
                                    alt7=1;
                                }
                                else if ( (true) ) {
                                    alt7=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 7, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 7, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:70:6: {...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT
                    {
                    if ( !((dayFirst)) ) {
                        throw new FailedPredicateException(input, "fullDate", "$dayFirst");
                    }
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_fullDate216); 
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate218); 
                    pt2=(Token)match(input,INT,FOLLOW_INT_in_fullDate232); 
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate234); 
                    pt3=(Token)match(input,INT,FOLLOW_INT_in_fullDate248); 

                    						 	 // year first
                    						 	 if ( pt1.getText().length() > 2 )
                    						 	 {
                    						 	 	 millis = parseFullDate( pt2.getText(),
                    																  pt3.getText(),
                    						 	 									  pt1.getText() );
                    						 	 }
                    						 	 
                    						 	 // year last
                    						 	 else
                    						 	 {
                    				 		 	 	 millis = parseFullDate( pt1.getText(),
                    						 	 	    							  pt2.getText(),
                    						 	  									  pt3.getText() );
                    						 	  }
                    					  	  

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:91:11: pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT
                    {
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_fullDate281); 
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate283); 
                    pt2=(Token)match(input,INT,FOLLOW_INT_in_fullDate299); 
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate301); 
                    pt3=(Token)match(input,INT,FOLLOW_INT_in_fullDate317); 

                    }
                    break;

            }


            	  	  				    // year first
            						 	 if ( pt1.getText().length() > 2 )
            						 	 {
            						 	 	 millis = parseFullDate( pt3.getText(),
            																  pt2.getText(),
            						 	 									  pt1.getText() );
            						 	 }
            						 	 
            						 	 // year last
            						 	 else
            						 	 {
            				 		 	 	 millis = parseFullDate( pt2.getText(),
            						 	 	    							  pt1.getText(),
            						 	  									  pt3.getText() );
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
    // $ANTLR end "fullDate"

    // Delegated rules


 

    public static final BitSet FOLLOW_INT_in_timespec63 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_timespec73 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_timespec77 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_timespec87 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_timespec91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_timespec111 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOAT_in_timespec125 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_HOURS_in_timespec131 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_timespec149 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_MINUTES_in_timespec157 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_timespec173 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SECONDS_in_timespec181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_fullDate216 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate218 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate232 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate234 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_fullDate281 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate283 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate299 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate301 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate317 = new BitSet(new long[]{0x0000000000000002L});

}