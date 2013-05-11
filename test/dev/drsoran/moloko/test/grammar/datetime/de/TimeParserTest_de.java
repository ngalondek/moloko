package dev.drsoran.moloko.test.grammar.datetime.de;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserImpl;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.MolokoTestRunner_de;
import dev.drsoran.moloko.test.grammar.datetime.TimeParserTestHelper;


@RunWith( MolokoTestRunner_de.class )
public class TimeParserTest_de extends MolokoTestCase
{
   private TimeParserTestHelper timeParserTestHelper;
   
   
   
   @Override
   @Before
   public void setUp()
   {
      super.setUp();
      
      final ITimeParser timeParser = new TimeParserImpl( Locale.getDefault() );
      timeParserTestHelper = new TimeParserTestHelper( timeParser );
   }
   
   
   
   @Test
   public void at_Time_hour_min_24h_no_seperator()
   {
      timeParserTestHelper.parseTime( "@1310", 13, 10, 0 );
   }
   
   
   
   @Test
   public void at_Time_hour_min_12h_colon()
   {
      timeParserTestHelper.parseTime( "um 11:00 vorm.", 11, 00, 00 );
   }
   
   
   
   @Test
   public void at_Time_hour_12h()
   {
      timeParserTestHelper.parseTime( "@11 vormittags", 11, 00, 00 );
   }
   
   
   
   @Test
   public void at_Time_textual()
   {
      timeParserTestHelper.parseTime( "am mittag", 12, 0, 0 );
      timeParserTestHelper.parseTime( "@mitternacht", 23, 59, 59 );
   }
   
   
   
   @Test
   public void time_hour_min_sec_24h_colon()
   {
      timeParserTestHelper.parseTime( "12:13:25", 12, 13, 0 );
   }
   
   
   
   @Test
   public void time_hour_min_24h_dot()
   {
      timeParserTestHelper.parseTime( "12.13", 12, 13, 0 );
      
   }
   
   
   
   @Test
   public void time_hour_min_12h_no_seperator()
   {
      timeParserTestHelper.parseTime( "1100 nachm.", 23, 00, 00 );
   }
   
   
   
   @Test
   public void time_hour_min_12h_colon()
   {
      timeParserTestHelper.parseTime( "1:13 nachmittags", 13, 13, 00 );
   }
   
   
   
   @Test
   public void time_textual()
   {
      timeParserTestHelper.parseTime( "mittags", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour()
   {
      timeParserTestHelper.parseTimeSpec( "12", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_no_seperator()
   {
      timeParserTestHelper.parseTimeSpec( "1210", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_colon()
   {
      timeParserTestHelper.parseTimeSpec( "12:13", 12, 13, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_colon()
   {
      timeParserTestHelper.parseTimeSpec( "12:13:25", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_hour_unit()
   {
      timeParserTestHelper.parseTimeSpec( "12 h", 12, 0, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_unit()
   {
      timeParserTestHelper.parseTimeSpec( "12 h 13 minuten", 12, 13, 0 );
   }
   
   
   
   @Test
   public void timespec_hour_min_sec_unit()
   {
      timeParserTestHelper.parseTimeSpec( "12 h 13 minuten 25 sec", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_min_hour_sec_unit()
   {
      timeParserTestHelper.parseTimeSpec( "13 minuten 12 h 25 sek", 12, 13, 25 );
   }
   
   
   
   @Test
   public void timespec_hour_sec_hour_unit()
   {
      timeParserTestHelper.parseTimeSpec( "12 stunden 25 sec 1 h", 13, 0, 25 );
   }
   
   
   
   @Test
   public void timespec_decimal_hour_sec_unit()
   {
      timeParserTestHelper.parseTimeSpec( "1.5 stunden 25 sekunden", 1, 30, 25 );
   }
   
   
   
   @Test
   public void timeEstiate_day_min()
   {
      timeParserTestHelper.parseTimeEstimate( "1 tag 15 min", 1 * 24, 15, 0 );
   }
   
   
   
   @Test
   public void timeEstiate_hour_min_days_decimal_hour()
   {
      timeParserTestHelper.parseTimeEstimate( "1 h 15 min 2 tage 1.5 stunden ",
                                              2 * 24 + 2,
                                              45,
                                              0 );
   }
   
   
   
   @Test
   public void timeEstiate_min_sec()
   {
      timeParserTestHelper.parseTimeEstimate( "1 min 1 sekunde", 0, 1, 1 );
   }
}
