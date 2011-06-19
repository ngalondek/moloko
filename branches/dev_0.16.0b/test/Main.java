import java.text.ParseException;


public class Main
{
   
   /**
    * @param args
    */
   public static void main( String[] args )
   {
      DateParserTestCase.execute();
      
      TimeParserTestCase_en.execute();
      TimeParserTestCase_de.execute();
      
      try
      {
         RecurrenceTestCase_en.execute();
         RecurrenceTestCase_de.execute();
                  
         RecurrencePatternTestCase.execute();
      }
      catch ( ParseException e )
      {
         e.printStackTrace();
      }
   }
}
