import java.text.SimpleDateFormat;
import java.util.Date;

import dev.drsoran.moloko.grammar.IDateFormatContext;


class TestDateFormaterContext_de implements IDateFormatContext
{
   public TestDateFormaterContext_de()
   {
   }
   
   @Override
   public String formatDateNumeric( long millis )
   {
      return new SimpleDateFormat( "d.M.yyyy" ).format( new Date( millis ) );
   }

   @Override
   public String getNumericDateFormatPattern( boolean withYear )
   {
      if ( withYear )
         return "d.M.yyyy";
      else
         return "d.M";
   }

   @Override
   public String formatDateNumeric( String part1, String part2 )
   {
      return String.format( "%s.%s", part1, part2 );
   }

   @Override
   public String formatDateNumeric( String part1,
                                    String part2,
                                    String part3 )
   {
      return String.format( "%s.%s.%s", part1, part2, part3 );
   }
}

