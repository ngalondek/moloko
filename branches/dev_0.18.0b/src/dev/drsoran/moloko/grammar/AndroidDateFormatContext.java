package dev.drsoran.moloko.grammar;

import android.content.Context;
import dev.drsoran.moloko.format.MolokoDateFormatter;


public class AndroidDateFormatContext implements IDateFormatContext
{
   private final Context context;
   
   
   
   public AndroidDateFormatContext( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public String formatDateNumeric( long millis )
   {
      return MolokoDateFormatter.formatDate( context,
                                             millis,
                                             MolokoDateFormatter.FORMAT_NUMERIC
                                                | MolokoDateFormatter.FORMAT_WITH_YEAR );
   }
   
   
   
   @Override
   public String formatDateNumeric( String part1, String part2 )
   {
      return MolokoDateFormatter.formatDateNumeric( context, part1, part2 );
   }
   
   
   
   @Override
   public String formatDateNumeric( String part1, String part2, String part3 )
   {
      return MolokoDateFormatter.formatDateNumeric( context,
                                                    part1,
                                                    part2,
                                                    part3 );
   }
   
   
   
   @Override
   public String getNumericDateFormatPattern( boolean withYear )
   {
      return MolokoDateFormatter.getNumericDateFormatPattern( context, withYear );
   }
   
}
