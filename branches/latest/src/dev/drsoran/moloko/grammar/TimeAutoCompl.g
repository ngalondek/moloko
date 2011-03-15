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


   private void setSuggId( int id )
   {
      quantifier = -1;
      suggId = id;
   }


   private void setSuggId( int id, Token t ) throws RecognitionException
   {
      qty( t );
      suggId = id;
   }

   
   private Token qty( Token t ) throws RecognitionException
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
   
   
   private List< String > getSuggestions()
   {
      if ( quantifier != -1 )
         return lang.getSuggestions( suggId, "units", quantifier );
      else
         return lang.getSuggestions( suggId );
   }
}

/** RULES **/

suggestTime returns [ List< String > suggs ]
   @init
   {
      setSuggId( 0 );
   }
   @after
   {
      suggs = getSuggestions();
   }
   : (AT | COMMA)?   {                        
                        setSuggId( 1 );
                     }
                     time_in_words
                   | v=INT {
                              setSuggId( 2, v );
                           } (    
                                  time_colon_spec 
                                | time_in_units (time_naturalspec time_naturalspec?)?
                             )
   ;
   catch[ RecognitionException e ]
   {
      suggs = getSuggestions();
   }
   
suggTimeEstimate returns[ List< String > suggs ]
   @after
   {
      suggs = getSuggestions();
   }
   : v=INT (
             {
                setSuggId( 10, v );
             }
             (DAYS | time_in_units)
           )
     {     
       setSuggId( 12 );
     }
     ((COMMA | AND) {
                       setSuggId( 11 );
                    }
                    time_naturalspec)*
   ;
   catch[ RecognitionException e ]
   {
      suggs = getSuggestions();
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
      