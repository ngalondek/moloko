package dev.drsoran.moloko.grammar.recurrence;

import android.content.Context;
import dev.drsoran.moloko.grammar.IDateFormatter;
import dev.drsoran.moloko.util.MolokoDateUtils;


public class RecurrencePatternDateFormatter implements IDateFormatter
{
   private final Context context;
   
   

   public RecurrencePatternDateFormatter( Context context )
   {
      this.context = context;
   }
   


   @Override
   public String formatDate( long millis )
   {
      return MolokoDateUtils.formatDate( context,
                                         millis,
                                         MolokoDateUtils.FORMAT_NUMERIC );
   }
}
