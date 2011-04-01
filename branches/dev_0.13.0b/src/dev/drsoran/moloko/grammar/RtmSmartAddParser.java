// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g 2011-04-01 14:06:37

   package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RtmSmartAddParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRING", "OP_DUE_DATE", "OP_ESTIMATE", "OP_LIST_OR_TAGS", "OP_LOCATION", "OP_PRIORITY", "OP_REPEAT"
    };
    public static final int EOF=-1;
    public static final int STRING=4;
    public static final int OP_DUE_DATE=5;
    public static final int OP_ESTIMATE=6;
    public static final int OP_LIST_OR_TAGS=7;
    public static final int OP_LOCATION=8;
    public static final int OP_PRIORITY=9;
    public static final int OP_REPEAT=10;

    // delegates
    // delegators


        public RtmSmartAddParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public RtmSmartAddParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return RtmSmartAddParser.tokenNames; }
    public String getGrammarFileName() { return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g"; }


       static final int SUGG_DUE           = 0;
       static final int SUGG_ESTIMATE      = 1;
       static final int SUGG_LIST_AND_TAGS = 2;
       static final int SUGG_LOCATION      = 3;
       static final int SUGG_PRIORITY      = 4;
       static final int SUGG_REPEAT        = 5;
       
       static final int HAS_DUE            = 1 << 0;
       static final int HAS_ESTIMATE       = 1 << 1;
       static final int HAS_LIST_AND_TAGS  = 1 << 2;
       static final int HAS_LOCATION       = 1 << 3;
       static final int HAS_PRIORITY       = 1 << 4;
       static final int HAS_REPEAT         = 1 << 5;



    // $ANTLR start "parseSmartAdd"
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:37:1: parseSmartAdd : name= STRING ( ( OP_DUE_DATE due= STRING ) | ( OP_ESTIMATE est= STRING ) | ( OP_LIST_OR_TAGS tag= STRING ) | ( OP_LOCATION loc= STRING ) | ( OP_PRIORITY pri= STRING ) | ( OP_REPEAT rep= STRING ) )* ;
    public final void parseSmartAdd() throws RecognitionException {
        Token name=null;
        Token due=null;
        Token est=null;
        Token tag=null;
        Token loc=null;
        Token pri=null;
        Token rep=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:38:4: (name= STRING ( ( OP_DUE_DATE due= STRING ) | ( OP_ESTIMATE est= STRING ) | ( OP_LIST_OR_TAGS tag= STRING ) | ( OP_LOCATION loc= STRING ) | ( OP_PRIORITY pri= STRING ) | ( OP_REPEAT rep= STRING ) )* )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:38:6: name= STRING ( ( OP_DUE_DATE due= STRING ) | ( OP_ESTIMATE est= STRING ) | ( OP_LIST_OR_TAGS tag= STRING ) | ( OP_LOCATION loc= STRING ) | ( OP_PRIORITY pri= STRING ) | ( OP_REPEAT rep= STRING ) )*
            {
            name=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd44); 
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:39:4: ( ( OP_DUE_DATE due= STRING ) | ( OP_ESTIMATE est= STRING ) | ( OP_LIST_OR_TAGS tag= STRING ) | ( OP_LOCATION loc= STRING ) | ( OP_PRIORITY pri= STRING ) | ( OP_REPEAT rep= STRING ) )*
            loop1:
            do {
                int alt1=7;
                switch ( input.LA(1) ) {
                case OP_DUE_DATE:
                    {
                    alt1=1;
                    }
                    break;
                case OP_ESTIMATE:
                    {
                    alt1=2;
                    }
                    break;
                case OP_LIST_OR_TAGS:
                    {
                    alt1=3;
                    }
                    break;
                case OP_LOCATION:
                    {
                    alt1=4;
                    }
                    break;
                case OP_PRIORITY:
                    {
                    alt1=5;
                    }
                    break;
                case OP_REPEAT:
                    {
                    alt1=6;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:40:9: ( OP_DUE_DATE due= STRING )
            	    {
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:40:9: ( OP_DUE_DATE due= STRING )
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:41:12: OP_DUE_DATE due= STRING
            	    {
            	    match(input,OP_DUE_DATE,FOLLOW_OP_DUE_DATE_in_parseSmartAdd72); 
            	    due=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd80); 

            	               

            	    }


            	    }
            	    break;
            	case 2 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:45:9: ( OP_ESTIMATE est= STRING )
            	    {
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:45:9: ( OP_ESTIMATE est= STRING )
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:46:12: OP_ESTIMATE est= STRING
            	    {
            	    match(input,OP_ESTIMATE,FOLLOW_OP_ESTIMATE_in_parseSmartAdd126); 
            	    est=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd134); 

            	               

            	    }


            	    }
            	    break;
            	case 3 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:50:9: ( OP_LIST_OR_TAGS tag= STRING )
            	    {
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:50:9: ( OP_LIST_OR_TAGS tag= STRING )
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:51:12: OP_LIST_OR_TAGS tag= STRING
            	    {
            	    match(input,OP_LIST_OR_TAGS,FOLLOW_OP_LIST_OR_TAGS_in_parseSmartAdd180); 
            	    tag=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd184); 

            	               

            	    }


            	    }
            	    break;
            	case 4 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:55:9: ( OP_LOCATION loc= STRING )
            	    {
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:55:9: ( OP_LOCATION loc= STRING )
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:56:12: OP_LOCATION loc= STRING
            	    {
            	    match(input,OP_LOCATION,FOLLOW_OP_LOCATION_in_parseSmartAdd230); 
            	    loc=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd238); 

            	               

            	    }


            	    }
            	    break;
            	case 5 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:60:9: ( OP_PRIORITY pri= STRING )
            	    {
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:60:9: ( OP_PRIORITY pri= STRING )
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:61:12: OP_PRIORITY pri= STRING
            	    {
            	    match(input,OP_PRIORITY,FOLLOW_OP_PRIORITY_in_parseSmartAdd284); 
            	    pri=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd292); 

            	               

            	    }


            	    }
            	    break;
            	case 6 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:65:9: ( OP_REPEAT rep= STRING )
            	    {
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:65:9: ( OP_REPEAT rep= STRING )
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:66:12: OP_REPEAT rep= STRING
            	    {
            	    match(input,OP_REPEAT,FOLLOW_OP_REPEAT_in_parseSmartAdd338); 
            	    rep=(Token)match(input,STRING,FOLLOW_STRING_in_parseSmartAdd348); 

            	               

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
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
    // $ANTLR end "parseSmartAdd"

    // Delegated rules


 

    public static final BitSet FOLLOW_STRING_in_parseSmartAdd44 = new BitSet(new long[]{0x00000000000007E2L});
    public static final BitSet FOLLOW_OP_DUE_DATE_in_parseSmartAdd72 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_parseSmartAdd80 = new BitSet(new long[]{0x00000000000007E2L});
    public static final BitSet FOLLOW_OP_ESTIMATE_in_parseSmartAdd126 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_parseSmartAdd134 = new BitSet(new long[]{0x00000000000007E2L});
    public static final BitSet FOLLOW_OP_LIST_OR_TAGS_in_parseSmartAdd180 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_parseSmartAdd184 = new BitSet(new long[]{0x00000000000007E2L});
    public static final BitSet FOLLOW_OP_LOCATION_in_parseSmartAdd230 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_parseSmartAdd238 = new BitSet(new long[]{0x00000000000007E2L});
    public static final BitSet FOLLOW_OP_PRIORITY_in_parseSmartAdd284 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_parseSmartAdd292 = new BitSet(new long[]{0x00000000000007E2L});
    public static final BitSet FOLLOW_OP_REPEAT_in_parseSmartAdd338 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_STRING_in_parseSmartAdd348 = new BitSet(new long[]{0x00000000000007E2L});

}