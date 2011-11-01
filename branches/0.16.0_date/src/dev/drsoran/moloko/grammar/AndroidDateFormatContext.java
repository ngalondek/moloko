package dev.drsoran.moloko.grammar;

import android.content.Context;
import dev.drsoran.moloko.util.MolokoDateUtils;


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
      return MolokoDateUtils.formatDate( context,
                                         millis,
                                         MolokoDateUtils.FORMAT_NUMERIC
                                            | MolokoDateUtils.FORMAT_WITH_YEAR );
   }
   
   
   
   @Override
   public String formatDateNumeric( String part1, String part2 )
   {
      return MolokoDateUtils.formatDateNumeric( context, part1, part2 );
   }
   
   
   
   @Override
   public String formatDateNumeric( String part1, String part2, String part3 )
   {
      return MolokoDateUtils.formatDateNumeric( context, part1, part2, part3 );
   }
   
   
   
   @Override
   public String getNumericDateFormatPattern( boolean withYear )
   {
      return MolokoDateUtils.getNumericDateFormatPattern( context, withYear );
   }
   
}
