parser grammar TimeAutoCompl;

options
{
   language=Java;
   tokenVocab=DateTimeLexer;
   k=1;
}

@header
{
   package dev.drsoran.moloko.grammar;
   
   import dev.drsoran.moloko.grammar.lang.AutoComplLanguage;
   
   import java.util.Arrays;
   import java.util.HashMap;
   import java.util.List;
   import java.util.Map;
}

@members
{
   public TimeAutoCompl()
   {
      super( null );
   }
 
   
   private int suggId = -1;
   
   private int quantifier = 1;
   
   private AutoComplLanguage lang;


   public void setLanguage( AutoComplLanguage lang )
   {
      this.lang = lang;
   }

   
   Token qty( Token t ) throws RecognitionException
   {
      try
      {
         quantifier = Integer.parseInt( t.getText() ); 
      }
   	catch( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
      
      return t;
   }
}

/** RULES **/

suggestTime returns [ List< String > suggs ]
   @init
   {
      suggId = 0;
   }
   : (AT | COMMA)?   {
                        suggId = 1;
                     }
                     time_in_words
                   | v=INT {
                              qty( v );
                              suggId = 2;
                           } (    
                                  time_colon_spec 
                                | time_in_units (time_naturalspec time_naturalspec?)?
                             )
   {
      suggs = lang.getSuggestions( suggId, "units", quantifier );
   }
   ;
   catch[ RecognitionException e ]
   {
      suggs = lang.getSuggestions( suggId, "units", quantifier );
   }
   
suggTimeEstimate returns[ List< String > suggs ]
   : v=INT (
             {
                qty( v );
                suggId = 10;
             }
             (DAYS | time_in_units)
           )
     {
       suggId = 12;
     }
     ((COMMA | AND) {
                       suggId = 11;
                    }
                    time_naturalspec)*
     {
        suggs  = lang.getSuggestions( suggId, "units", quantifier );
     }
   ;
   catch[ RecognitionException e ]
   {
      suggs  = lang.getSuggestions( suggId, "units", quantifier );
   }

time_in_words
   : NEVER
   | MIDNIGHT
   | (MIDDAY | NOON)
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }
   
time_in_units
   : (HOURS | MINUTES | SECONDS)
   ;   
   catch[ RecognitionException e ]
   {
      throw e;
   }

time_colon_spec
	: (COLON | DOT { 
	                  suggId = 3;
	               }) INT ( A | AM | PM)?
	;
   catch[ RecognitionException e ]
   {
      throw e;
   }

time_naturalspec
   : v=INT { qty( v ); } time_in_units
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }
      