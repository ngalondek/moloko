package dev.drsoran.moloko.grammar.lang;

import java.util.HashMap;


public class RecurrPatternLanguage
{
   protected final HashMap< String, String > dictionary = new HashMap< String, String >();
   
   

   public RecurrPatternLanguage()
   {
      dictionary.put( "every_year_1", "every year" );
      dictionary.put( "every_year_n", "every %s years" );
      dictionary.put( "every_month_1", "every month" );
      dictionary.put( "every_month_n", "every %s months" );
      dictionary.put( "every_week_1", "every week" );
      dictionary.put( "every_week_n", "every %s weeks" );
      dictionary.put( "every_day_1", "every day" );
      dictionary.put( "every_day_n", "every %s days" );
            
      dictionary.put( "after_year_1", "after a year" );
      dictionary.put( "after_year_n", "after %s years" );
      dictionary.put( "after_month_1", "after a month" );
      dictionary.put( "after_month_n", "after %s months" );
      dictionary.put( "after_week_1", "after a week" );
      dictionary.put( "after_week_n", "after %s weeks" );
      dictionary.put( "after_day_1", "after a day" );
      dictionary.put( "after_day_n", "after %s days" );
      
      dictionary.put( "MO", "Monday" );
      dictionary.put( "TU", "Tuesday" );
      dictionary.put( "WE", "Wednesday" );
      dictionary.put( "TH", "Thursday" );
      dictionary.put( "FR", "Friday" );
      dictionary.put( "SA", "Saturday" );
      dictionary.put( "SO", "Sunday" );
      
      dictionary.put( "m1", "January" );
      dictionary.put( "m2", "February" );
      dictionary.put( "m3", "March" );
      dictionary.put( "m4", "April" );
      dictionary.put( "m5", "May" );
      dictionary.put( "m6", "June" );
      dictionary.put( "m7", "July" );
      dictionary.put( "m8", "August" );
      dictionary.put( "m9", "September" );
      dictionary.put( "m10", "October" );
      dictionary.put( "m11", "November" );
      dictionary.put( "m12", "December" );
      
      dictionary.put( "on_the", "on the" );
   }
   


   public void add( StringBuilder sb, String key )
   {
      final String res = dictionary.get( key );
      
      if ( res == null )
         sb.append( key );
      else
         sb.append( res );
   }
   


   public void addEvery( StringBuilder sb, String unit, String quantity )
   {
      addPlural( sb, "every", unit, quantity );
   }
   


   public void addAfter( StringBuilder sb, String unit, String quantity )
   {
      addPlural( sb, "after", unit, quantity );
   }
   


   public void addStToX( StringBuilder sb, int x )
   {
      final String xStr = String.valueOf( x );
      
      sb.append( xStr );
      
      final String xst = dictionary.get( "xst" );
      
      if ( xst != null )
         sb.append( xst );
      else
      {
         if ( x > 3 && x < 20 )
         {
            sb.append( "th" );
         }
         else
         {
            final char lastNum = xStr.charAt( xStr.length() - 1 );
            
            switch ( lastNum )
            {
               case '1':
                  sb.append( "st" );
                  break;
               case '2':
                  sb.append( "nd" );
                  break;
               case '3':
                  sb.append( "rd" );
                  break;
               default :
                  sb.append( "th" );
                  break;
            }
         }
      }
   }
   


   private void addPlural( StringBuilder sb,
                           String prefix,
                           String unit,
                           String quantity )
   {
      String res = null;
      
      final String key = prefix + "_" + unit + "_";
      
      res = dictionary.get( key + quantity );
      
      if ( res == null )
         res = dictionary.get( key + "n" );
      
      if ( res != null )
      {
         sb.append( String.format( res, quantity ) );
      }
   }
}
