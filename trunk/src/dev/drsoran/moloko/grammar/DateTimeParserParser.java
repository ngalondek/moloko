// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g 2010-07-07 08:46:52

	package dev.drsoran.moloko.grammar;
	
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.debug.*;
import java.io.IOException;
public class DateTimeParserParser extends DebugParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "DATE_SEP", "NOW", "TODAY", "TOMORROW", "TONIGHT", "YESTERDAY", "WS"
    };
    public static final int TODAY=7;
    public static final int WS=11;
    public static final int TONIGHT=9;
    public static final int NOW=6;
    public static final int INT=4;
    public static final int DATE_SEP=5;
    public static final int EOF=-1;
    public static final int YESTERDAY=10;
    public static final int TOMORROW=8;

    // delegates
    // delegators

    public static final String[] ruleNames = new String[] {
        "invalidRule", "fullDate", "doNotCall"
    };
     
        public int ruleLevel = 0;
        public int getRuleLevel() { return ruleLevel; }
        public void incRuleLevel() { ruleLevel++; }
        public void decRuleLevel() { ruleLevel--; }
        public DateTimeParserParser(TokenStream input) {
            this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
        }
        public DateTimeParserParser(TokenStream input, int port, RecognizerSharedState state) {
            super(input, state);
            DebugEventSocketProxy proxy =
                new DebugEventSocketProxy(this, port, null);
            setDebugListener(proxy);
            try {
                proxy.handshake();
            }
            catch (IOException ioe) {
                reportError(ioe);
            }
        }
    public DateTimeParserParser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg, new RecognizerSharedState());

    }
    protected boolean evalPredicate(boolean result, String predicate) {
        dbg.semanticPredicate(result, predicate);
        return result;
    }


    public String[] getTokenNames() { return DateTimeParserParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g"; }

    	
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:44:1: doNotCall : ;
    public final void doNotCall() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "doNotCall");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(44, 1);

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:44:11: ()
            dbg.enterAlt(1);

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:44:13: 
            {
            }

        }
        finally {
        }
        dbg.location(44, 13);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "doNotCall");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "doNotCall"


    // $ANTLR start "fullDate"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:46:1: fullDate[boolean dayFirst] returns [long millis] : ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT ) ;
    public final long fullDate(boolean dayFirst) throws RecognitionException {
        long millis = 0;

        Token pt1=null;
        Token pt2=null;
        Token pt3=null;

        try { dbg.enterRule(getGrammarFileName(), "fullDate");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(46, 1);

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:47:2: ( ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT ) )
            dbg.enterAlt(1);

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:47:4: ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT )
            {
            dbg.location(47,4);
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:47:4: ({...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT | pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT )
            int alt1=2;
            try { dbg.enterSubRule(1);
            try { dbg.enterDecision(1);

            int LA1_0 = input.LA(1);

            if ( (LA1_0==INT) ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1==DATE_SEP) ) {
                    int LA1_2 = input.LA(3);

                    if ( (LA1_2==INT) ) {
                        int LA1_3 = input.LA(4);

                        if ( (LA1_3==DATE_SEP) ) {
                            int LA1_4 = input.LA(5);

                            if ( (LA1_4==INT) ) {
                                int LA1_5 = input.LA(6);

                                if ( (evalPredicate(dayFirst,"$dayFirst")) ) {
                                    alt1=1;
                                }
                                else if ( (true) ) {
                                    alt1=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 1, 5, input);

                                    dbg.recognitionException(nvae);
                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 4, input);

                                dbg.recognitionException(nvae);
                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 3, input);

                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 2, input);

                        dbg.recognitionException(nvae);
                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(1);}

            switch (alt1) {
                case 1 :
                    dbg.enterAlt(1);

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:47:6: {...}?pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT
                    {
                    dbg.location(47,6);
                    if ( !(evalPredicate(dayFirst,"$dayFirst")) ) {
                        throw new FailedPredicateException(input, "fullDate", "$dayFirst");
                    }
                    dbg.location(47,22);
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_fullDate63); 
                    dbg.location(47,27);
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate65); 
                    dbg.location(48,14);
                    pt2=(Token)match(input,INT,FOLLOW_INT_in_fullDate79); 
                    dbg.location(48,19);
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate81); 
                    dbg.location(49,14);
                    pt3=(Token)match(input,INT,FOLLOW_INT_in_fullDate95); 
                    dbg.location(50,9);

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
                    dbg.enterAlt(2);

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:68:12: pt1= INT DATE_SEP pt2= INT DATE_SEP pt3= INT
                    {
                    dbg.location(68,15);
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_fullDate129); 
                    dbg.location(68,20);
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate131); 
                    dbg.location(69,16);
                    pt2=(Token)match(input,INT,FOLLOW_INT_in_fullDate147); 
                    dbg.location(69,21);
                    match(input,DATE_SEP,FOLLOW_DATE_SEP_in_fullDate149); 
                    dbg.location(70,16);
                    pt3=(Token)match(input,INT,FOLLOW_INT_in_fullDate165); 
                    dbg.location(71,13);

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
                    break;

            }
            } finally {dbg.exitSubRule(1);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(89, 3);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "fullDate");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return millis;
    }
    // $ANTLR end "fullDate"

    // Delegated rules


 

    public static final BitSet FOLLOW_INT_in_fullDate63 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate65 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate79 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate81 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_fullDate129 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate131 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate147 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_DATE_SEP_in_fullDate149 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fullDate165 = new BitSet(new long[]{0x0000000000000002L});

}