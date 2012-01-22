import java.util.Locale;


public class TimeParserTestCase_de
{
   private static void parseTime( String string, int h, int m, int s )
   {
      DateTimeTestHelper.parseTime( Locale.GERMAN, string, h, m, s );
   }
   


   private static void parseTimeSpec( String string, int h, int m, int s )
   {
      DateTimeTestHelper.parseTimeSpec( Locale.GERMAN, string, h, m, s );
   }
   


   private static void parseTimeEstimate( String string, int h, int m, int s )
   {
      DateTimeTestHelper.parseTimeEstimate( Locale.GERMAN, string, h, m, s );
   }
   


   public final static void execute()
   {
      parseTime( "@1310", 13, 10, 0 );
      parseTime( "12:13:25", 12, 13, 0 );
      parseTime( "12.13", 12, 13, 0 );
      parseTime( "mittags", 12, 0, 0 );
      parseTime( "am mittag", 12, 0, 0 );
      parseTime( "@mitternacht", 23, 59, 59 );
      parseTime( "um 23:00", 23, 00, 00 );
      parseTime( "@11", 11, 00, 00 );
      parseTime( "1:13 vorm", 1, 13, 0 );
      parseTime( "1:13 vorm.", 1, 13, 0 );
      parseTime( "1:13 nachmittags", 13, 13, 0 );      
      
      parseTimeSpec( "12", 12, 0, 0 );
      parseTimeSpec( "1210", 12, 0, 0 );
      parseTimeSpec( "12:13", 12, 13, 0 );      
      parseTimeSpec( "12:13:25", 12, 13, 25 );
      parseTimeSpec( "12 h", 12, 0, 0 );
      parseTimeSpec( "12 h 13 minuten", 12, 13, 0 );
      parseTimeSpec( "12 h 13 minuten 25 sec", 12, 13, 25 );
      parseTimeSpec( "13 minuten 12 h 25 sek", 12, 13, 25 );
      parseTimeSpec( "12 stunden 25 sec 1 h", 13, 0, 25 );
      parseTimeSpec( "1.5 stunden 25 sekunden", 1, 30, 25 );
      
      parseTimeEstimate( "1 tag 15 min", 1 * 24, 15, 0 );
      parseTimeEstimate( "1 h 15 min 2 tage 1.5 stunden", 2 * 24 + 2, 45, 0 );
      parseTimeEstimate( "1 min 1 sekunde", 0, 1, 1 );
   }
}
