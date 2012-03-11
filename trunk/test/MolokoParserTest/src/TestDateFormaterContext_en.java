import java.text.SimpleDateFormat;
import java.util.Date;

import dev.drsoran.moloko.grammar.IDateFormatContext;


class TestDateFormaterContext_en implements IDateFormatContext
{
   public TestDateFormaterContext_en()
   {
   }
   
   @Override
   public String formatDateNumeric( long millis )
   {
      return new SimpleDateFormat( "MM/dd/yyyy" ).format( new Date( millis ) );
   }

   @Override
   public String getNumericDateFormatPattern( boolean withYear )
   {
      if ( withYear )
         return "MM/dd/yyyy";
      else
         return "MM/dd";
   }

   @Override
   public String formatDateNumeric( String part1, String part2 )
   {
      return String.format( "%s/%s", part1, part2 );
   }

   @Override
   public String formatDateNumeric( String part1,
                                    String part2,
                                    String part3 )
   {
      return String.format( "%s/%s/%s", part1, part2, part3 );
   }
}

