import java.util.Locale;


public class TimeParserTestCase_en
{
   private static void parseTime( String string, int h, int m, int s )
   {
      DateTimeTestHelper.parseTime( Locale.US, string, h, m, s );
   }
   
   
   
   private static void parseTimeSpec( String string, int h, int m, int s )
   {
      DateTimeTestHelper.parseTimeSpec( Locale.US, string, h, m, s );
   }
   
   
   
   private static void parseTimeEstimate( String string, int h, int m, int s )
   {
      DateTimeTestHelper.parseTimeEstimate( Locale.US, string, h, m, s );
   }
   
   
   
   public final static void execute()
   {
      parseTime( "@1310", 13, 10, 0 );
      parseTime( "12:13:25", 12, 13, 0 );
      parseTime( "12.13", 12, 13, 0 );
      parseTime( "midday", 12, 0, 0 );
      parseTime( "at noon", 12, 0, 0 );
      parseTime( "@midnight", 23, 59, 59 );
      parseTime( "at 11:00 am", 11, 00, 00 );
      parseTime( "1100p", 23, 00, 00 );
      parseTime( "@11a", 11, 00, 00 );
      
      parseTimeSpec( "12", 12, 0, 0 );
      parseTimeSpec( "1210", 12, 0, 0 );
      parseTimeSpec( "12:13", 12, 13, 0 );
      parseTimeSpec( "12:13:25", 12, 13, 25 );
      parseTimeSpec( "12 h", 12, 0, 0 );
      parseTimeSpec( "12 h 13 minutes", 12, 13, 0 );
      parseTimeSpec( "12 h 13 minutes 25 sec", 12, 13, 25 );
      parseTimeSpec( "13 minutes 12 h 25 sec", 12, 13, 25 );
      parseTimeSpec( "12 hours 25 sec 1 h", 13, 0, 25 );
      parseTimeSpec( "1.5 hours 25 sec", 1, 30, 25 );
      
      parseTimeEstimate( "1 day 15 min", 1 * 24, 15, 0 );
      parseTimeEstimate( "1 h 15 min 2 days 1.5 hours ", 2 * 24 + 2, 45, 0 );
      parseTimeEstimate( "1 min 1 second", 0, 1, 1 );
   }
}
