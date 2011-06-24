import java.text.ParseException;



public class Main
{
   
   /**
    * @param args
    */
   public static void main( String[] args )
   {      
      DateParserTestCase_en.execute();
      DateParserTestCase_de.execute();
      
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
