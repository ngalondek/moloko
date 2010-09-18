package dev.drsoran.moloko.util;

import java.text.DateFormat;

import dev.drsoran.moloko.MolokoApp;


public class MolokoDateUtils
{
   public final static String formatDate( long millis, int dateStyle )
   {
      final DateFormat df = DateFormat.getDateInstance( dateStyle,
                                                        MolokoApp.getSettings()
                                                                 .getLocale() );
      df.setTimeZone( MolokoApp.getSettings().getTimezone() );
      
      return df.format( millis );
   }
   


   public final static String formatDateTime( long millis, int dateStyle )
   {
      final DateFormat df = DateFormat.getDateTimeInstance( dateStyle,
                                                            DateFormat.SHORT,
                                                            MolokoApp.getSettings()
                                                                     .getLocale() );
      df.setTimeZone( MolokoApp.getSettings().getTimezone() );
      
      return df.format( millis );
   }
   


   public final static String formatTime( long millis )
   {
      final DateFormat df = DateFormat.getTimeInstance( DateFormat.SHORT,
                                                        MolokoApp.getSettings()
                                                                 .getLocale() );
      
      df.setTimeZone( MolokoApp.getSettings().getTimezone() );
      
      return df.format( millis );
   }
}
