package dev.drsoran.moloko.test.grammar.datetime;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.MolokoTestRunner_en;


@RunWith( MolokoTestRunner_en.class )
public class TimeParserTest_en extends MolokoTestCase
{
   @Test
   public void at_Time_hour_min_24h_no_seperator()
   {
      DateTimeTestHelper.parseTime( "@1310", 13, 10, 0 );
   }
   
   
   
   @Test
   public void at_Time_hour_min_12h_colon()
   {
      DateTimeTestHelper.parseTime( "at 11:00 am", 11, 00, 00 );
   }
   
   
   
   @Test
   public void at_Time_hour_12h()
   {
      DateTimeTestHelper.parseTime( "@11a", 11, 00, 00 );
   }
   
   
   
   @Test
   public void at_Time_textual()
   {
      DateTimeTestHelper.parseTime( "at noon", 12, 0, 0 );
      DateTimeTestHelper.parseTime( "@midnight", 23, 59, 59 );
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
      DateTimeTestHelper.parseTime( "1100p", 23, 00, 00 );
   }
   
   
   
   @Test
   public void time_textual()
   {
      DateTimeTestHelper.parseTime( "midday", 12, 0, 0 );
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
      DateTimeTestHelper.parseTimeSpec( "12 h 13 minutes", 12, 13, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "12 h 13 minutes 25 sec", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_min_hour_sec_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "13 minutes 12 h 25 sec", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_hour_sec_hour_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "12 hours 25 sec 1 h", 13, 0, 25 );
   }
   
   
   
   @Test
   public void timespec_decimal_hour_sec_unit()
   {
      DateTimeTestHelper.parseTimeSpec( "1.5 hours 25 sec", 1, 30, 25 );
   }
   
   
   
   @Test
   public void timeEstiate_day_min()
   {
      DateTimeTestHelper.parseTimeEstimate( "1 day 15 min", 1 * 24, 15, 0 );
   }
   
   
   
   @Test
   public void timeEstiate_hour_min_days_decimal_hour()
   {
      DateTimeTestHelper.parseTimeEstimate( "1 h 15 min 2 days 1.5 hours ",
                                            2 * 24 + 2,
                                            45,
                                            0 );
   }
   
   
   
   @Test
   public void timeEstiate_min_sec()
   {
      DateTimeTestHelper.parseTimeEstimate( "1 min 1 second", 0, 1, 1 );
   }
}
