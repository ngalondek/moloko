import java.text.ParseException;



public class Main
{
   
   /**
    * @param args
    */
   public static void main( String[] args )
   {
      DateParserTestCase.execute();
      TimeParserTestCase.execute();
      try
      {
         RecurrenceTestCase.execute();
         RecurrencePatternTestCase.execute();
      }
      catch ( ParseException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
