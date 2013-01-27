package dev.drsoran.moloko.test.grammar.datetime.de;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.test.MolokoTestRunner_de;
import dev.drsoran.moloko.test.grammar.datetime.DateTimeTestHelper;


@RunWith( MolokoTestRunner_de.class )
public class TimeParserTest_de
{
   @Test
   public void at_Time_hour_min_24h_no_seperator()
   {
      DateTimeTestHelper.parseTime( "@1310", 13, 10, 0 );
   }
   
   
   
   @Test
   public void at_Time_hour_min_12h_colon()
   {
      DateTimeTestHelper.parseTime( "um 11:00 vorm.", 11, 00, 00 );
   }
   
   
   
   @Test
   public void at_Time_hour_12h()
   {
      DateTimeTestHelper.parseTime( "@11 vormittags", 11, 00, 00 );
   }
   
   
   
   @Test
   public void at_Time_textual()
   {
      DateTimeTestHelper.parseTime( "am mittag", 12, 0, 0 );
      DateTimeTestHelper.parseTime( "@mitternacht", 23, 59, 59 );
   }
   
   
   
   @Test
   public void time_hour_min_sec_24h_colon()
   {
      DateTimeTestHelper.parseTime( "12:13:25", 12, 13, 0 );
   }
   
   
   
   @Test
   public void time_hour_min_24h_dot()
   {
      DateTimeTestHelper.parseTime( "12.13", 12, 13, 0 );
      
   }
   
   
   
   @Test
   public void time_hour_min_12h_no_seperator()
   {
      DateTimeTestHelper.parseTime( "1100 nachm.", 23, 00, 00 );
   }
   
   
   
   @Test
   public void time_hour_min_12h_colon()
   {
      DateTimeTestHelper.parseTime( "1:13 nachmittags", 13, 13, 00 );
   }
   
   
   
   @Test
   public void time_textual()
   {
      DateTimeTestHelper.parseTime( "mittags", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour()
   {
      DateTimeTestHelper.parseTimeSpec( "12", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_no_seperator()
   {
      DateTimeTestHelper.parseTimeSpec( "1210", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_colon()
   {
      DateTimeTestHelper.parseTimeSpec( "12:13", 12, 13, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_colon()
   {
      DateTimeTestHelper.parseTimeSpec( "12:13:25", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_hour_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "12 h", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "12 h 13 minuten", 12, 13, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "12 h 13 minuten 25 sec", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_min_hour_sec_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "13 minuten 12 h 25 sek", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_hour_sec_hour_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "12 stunden 25 sec 1 h", 13, 0, 25 );
   }
   
   
   
   @Test
   public void timespec_decimal_hour_sec_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "1.5 stunden 25 sekunden", 1, 30, 25 );
   }
   
   
   
   @Test
   public void timeEstiate_day_min()
   {
      DateTimeTestHelper.parseTimeEstimate( "1 tag 15 min", 1 * 24, 15, 0 );
   }
   
   
   
   @Test
   public void timeEstiate_hour_min_days_decimal_hour()
   {
      DateTimeTestHelper.parseTimeEstimate( "1 h 15 min 2 tage 1.5 stunden ",
                                            2 * 24 + 2,
                                            45,
                                            0 );
   }
   
   
   
   @Test
   public void timeEstiate_min_sec()
   {
      DateTimeTestHelper.parseTimeEstimate( "1 min 1 sekunde", 0, 1, 1 );
   }
}
