package dev.drsoran.moloko.util;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.Settings;
import android.content.Context;


public class DateUtils extends android.text.format.DateUtils
{
   public final static String formatDateTime( Context context,
                                              long millis,
                                              int flags )
   {
      if ( ( flags & FORMAT_SHOW_TIME ) != 0 )
      {
         if ( MolokoApp.getSettings().getTimeformat() == Settings.TIMEFORMAT_12 )
            flags |= FORMAT_12HOUR;
         else
            flags |= FORMAT_24HOUR;
         
      }
      
      return android.text.format.DateUtils.formatDateTime( context,
                                                           millis,
                                                           flags );
   }
}
