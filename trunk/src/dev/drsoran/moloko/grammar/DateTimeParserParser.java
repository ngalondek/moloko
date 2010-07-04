// $ANTLR 3.2 Sep 23, 2009 12:02:23 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g 2010-07-03 14:02:43

	package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DateTimeParserParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "FLOAT", "HOURS", "MINUTES", "SECONDS", "EXPONENT", "WS", "':'"
    };
    public static final int MINUTES=7;
    public static final int EXPONENT=9;
    public static final int WS=10;
    public static final int T__11=11;
    public static final int SECONDS=8;
    public static final int INT=4;
    public static final int FLOAT=5;
    public static final int EOF=-1;
    public static final int HOURS=6;

    // delegates
    // delegators


        public DateTimeParserParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public DateTimeParserParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return DateTimeParserParser.tokenNames; }
    public String getGrammarFileName() { return "F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g"; }


    	private final static long SECOND_MILLIS = 1000;
    		
    	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    	
    	private final static long HOUR_MILLIS = 60 * MINUTE_MILLIS;



    // $ANTLR start "statement"
    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:27:1: statement : timespec ;
    public final void statement() throws RecognitionException {
        try {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:28:2: ( timespec )
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:28:4: timespec
            {
            pushFollow(FOLLOW_timespec_in_statement43);
            timespec();

            state._fsp--;


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
    // $ANTLR end "statement"


    // $ANTLR start "timespec"
    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:31:1: timespec returns [ long millis ] : (h= INT ':' m= INT ( ':' s= INT )? | ( (h= INT | h= FLOAT ) HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? );
    public final long timespec() throws RecognitionException {
        long millis = 0;

        Token h=null;
        Token m=null;
        Token s=null;

        try {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:32:2: (h= INT ':' m= INT ( ':' s= INT )? | ( (h= INT | h= FLOAT ) HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INT) ) {
                int LA6_1 = input.LA(2);

                if ( ((LA6_1>=HOURS && LA6_1<=SECONDS)) ) {
                    alt6=2;
                }
                else if ( (LA6_1==11) ) {
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
                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:32:9: h= INT ':' m= INT ( ':' s= INT )?
                    {
                    h=(Token)match(input,INT,FOLLOW_INT_in_timespec65); 
                     millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   
                    match(input,11,FOLLOW_11_in_timespec75); 
                    m=(Token)match(input,INT,FOLLOW_INT_in_timespec79); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:34:4: ( ':' s= INT )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==11) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:34:5: ':' s= INT
                            {
                            match(input,11,FOLLOW_11_in_timespec89); 
                            s=(Token)match(input,INT,FOLLOW_INT_in_timespec93); 

                            }
                            break;

                    }

                     millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                    }
                    break;
                case 2 :
                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:36:4: ( (h= INT | h= FLOAT ) HOURS )? (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:36:4: ( (h= INT | h= FLOAT ) HOURS )?
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
                            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:36:6: (h= INT | h= FLOAT ) HOURS
                            {
                            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:36:6: (h= INT | h= FLOAT )
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
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:36:10: h= INT
                                    {
                                    h=(Token)match(input,INT,FOLLOW_INT_in_timespec113); 
                                     millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   

                                    }
                                    break;
                                case 2 :
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:37:8: h= FLOAT
                                    {
                                    h=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_timespec127); 
                                     millis += Float.parseFloat( (h!=null?h.getText():null) ) * HOUR_MILLIS;   

                                    }
                                    break;

                            }

                            match(input,HOURS,FOLLOW_HOURS_in_timespec133); 

                            }
                            break;

                    }

                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:38:6: (m= INT MINUTES )?
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
                            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:38:10: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_timespec151); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_timespec159); 

                            }
                            break;

                    }

                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:39:6: (s= INT SECONDS )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==INT) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:39:10: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_timespec175); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_timespec183); 

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

    // Delegated rules


 

    public static final BitSet FOLLOW_timespec_in_statement43 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_timespec65 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_timespec75 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_timespec79 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_11_in_timespec89 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_timespec93 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_timespec113 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_FLOAT_in_timespec127 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_HOURS_in_timespec133 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_timespec151 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_MINUTES_in_timespec159 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_timespec175 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SECONDS_in_timespec183 = new BitSet(new long[]{0x0000000000000002L});

}