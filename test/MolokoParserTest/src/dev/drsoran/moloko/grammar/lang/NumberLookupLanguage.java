package dev.drsoran.moloko.grammar.lang;

import java.util.HashMap;

public class NumberLookupLanguage
{
protected final HashMap< String, Integer > dictionary = new HashMap< String, Integer >();
   
   

   public NumberLookupLanguage()
   {
      dictionary.put( "one", 1 );
      dictionary.put( "two", 2 );
      dictionary.put( "three", 3 );
      dictionary.put( "four", 4 );
      dictionary.put( "five", 5 );
      dictionary.put( "six", 6 );
      dictionary.put( "seven", 7 );
      dictionary.put( "eight", 8 );
      dictionary.put( "nine", 9 );
      dictionary.put( "ten", 10 );
            
      dictionary.put( "eins", 1 );
      dictionary.put( "zwei", 2 );
      dictionary.put( "drei", 3 );
      dictionary.put( "vier", 4 );
      dictionary.put( "fünf", 5 );
      dictionary.put( "funf", 5 );
      dictionary.put( "sechs", 6 );
      dictionary.put( "sieben", 7 );
      dictionary.put( "acht", 8 );
      dictionary.put( "neun", 9 );
      dictionary.put( "zehn", 10 );
      
      dictionary.put( "jan", 0 );
      dictionary.put( "feb", 1 );
      dictionary.put( "mar", 2 );
      dictionary.put( "apr", 3 );
      dictionary.put( "may", 4 );
      dictionary.put( "jun", 5 );
      dictionary.put( "jul", 6 );
      dictionary.put( "aug", 7 );
      dictionary.put( "sep", 8 );
      dictionary.put( "oct", 9 );
      dictionary.put( "nov", 10 );
      dictionary.put( "dec", 11 );
      
      dictionary.put( "mär", 2 );
      dictionary.put( "mai", 4 );
      dictionary.put( "okt", 9 );
      dictionary.put( "dez", 11 );
      
      dictionary.put( "mo", 2 );
      dictionary.put( "tu", 3 );
      dictionary.put( "we", 4 );
      dictionary.put( "th", 5 );
      dictionary.put( "fr", 6 );
      dictionary.put( "sa", 7 );
      dictionary.put( "so", 1 );
      
      dictionary.put( "di", 3 );
      dictionary.put( "mi", 4 );
      dictionary.put( "do", 5 );
      dictionary.put( "so", 1 );
   }



   public Integer get( Object arg0 )
   {
      return dictionary.get( arg0 );
   }
   
   
}
