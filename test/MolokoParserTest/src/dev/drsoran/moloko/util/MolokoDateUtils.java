package dev.drsoran.moloko.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class MolokoDateUtils
{
   private final static SimpleDateFormat SDF_PARSE = new SimpleDateFormat( "dd.MM.yyyy" );
   
   public final static int FORMAT_WITH_YEAR = 1 << 0;
   
   public final static int FORMAT_NUMERIC = 1 << 1;
   
   public final static int FORMAT_SHOW_WEEKDAY = 1 << 2;
   
   public final static int FORMAT_PARSER = FORMAT_WITH_YEAR | FORMAT_NUMERIC
      | ( 1 << 3 );
   
   

   public final static String formatDate( String pattern,
                                          String value,
                                          int dateStyle )
   {
      final SimpleDateFormat sdf = new SimpleDateFormat( pattern );
      
      try
      {
         sdf.parse( value );
         
         return SDF_PARSE.format( sdf.getCalendar().getTime() );
      }
      catch ( ParseException e )
      {
         return null;
      }
   }
   


   public final static Calendar newCalendar()
   {
      return Calendar.getInstance();
   }
   


   public final static Calendar newCalendar( long millis )
   {
      final Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis( millis );
      return cal;
   }
   
   
   public final static Calendar clearTime( Calendar cal )
   {
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      cal.clear( Calendar.MILLISECOND );
      cal.clear( Calendar.AM_PM );
      cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      return cal;
   }
}
