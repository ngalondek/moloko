import java.text.ParseException;


public class Main
{
   
   /**
    * @param args
    */
   public static void main( String[] args )
   {
      DateParserTestCase_en.execute(new TestDateFormaterContext());
      DateParserTestCase_de.execute(new TestDateFormaterContext());
      
      TimeParserTestCase_en.execute();
      TimeParserTestCase_de.execute();
      
      try
      {
         RecurrenceTestCase_en.execute(new TestDateFormaterContext());
         RecurrenceTestCase_de.execute(new TestDateFormaterContext());
         
         RecurrencePatternTestCase.execute();
      }
      catch ( ParseException e )
      {
         e.printStackTrace();
      }
   }
}
