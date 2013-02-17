package dev.drsoran.moloko.test.grammar.datetime.de;

import java.util.Calendar;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.test.MolokoTestRunner_de;
import dev.drsoran.moloko.test.grammar.datetime.DateParserTestBase;
import dev.drsoran.moloko.test.grammar.datetime.DateTimeTestHelper;
import dev.drsoran.moloko.util.MolokoCalendar;


@RunWith( MolokoTestRunner_de.class )
public class DateParserTest_de extends DateParserTestBase
{
   @Test
   public void date_time_now()
   {
      final Calendar cal = Calendar.getInstance();
      parseDate( "jetzt",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 cal.get( Calendar.HOUR_OF_DAY ),
                 cal.get( Calendar.MINUTE ),
                 cal.get( Calendar.SECOND ) );
   }
   
   
   
   @Test
   public void time_1pm()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.setTimeInMillis( System.currentTimeMillis() );
      cal.add( Calendar.HOUR_OF_DAY, 1 );
      
      final int hour_24 = cal.get( Calendar.HOUR_OF_DAY );
      final String time = String.format( "%d:00", hour_24 );
      
      parseDate( time,
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 hour_24,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void time_0pm()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.setTimeInMillis( System.currentTimeMillis() );
      
      final int hour_24 = cal.get( Calendar.HOUR_OF_DAY );
      cal.add( Calendar.DAY_OF_WEEK, 1 );
      
      final String time = String.format( "%d:00", hour_24 );
      
      parseDate( time,
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 hour_24,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void date_time_never()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.setHasDate( false );
      cal.setHasTime( false );
      
      parseDate( "nie",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 cal.get( Calendar.HOUR ),
                 cal.get( Calendar.MINUTE ),
                 cal.get( Calendar.SECOND ),
                 false,
                 false );
   }
   
   
   
   @Test
   public void date_today()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      parseDate( "heute",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_today_at_time()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      parseDate( "heute@12",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 12,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void date_today_at_time1()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      parseDate( "heute@1310",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 13,
                 10,
                 0 );
   }
   
   
   
   @Test
   public void date_tomorrow_at_time()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.add( Calendar.DAY_OF_MONTH, 1 );
      parseDate( "morgen@9",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 9,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void date_end_of_month()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.DAY_OF_MONTH,
               cal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
      parseDate( "ende des monats",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_in_year_month_at_time()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.add( Calendar.YEAR, 1 );
      cal.add( Calendar.MONTH, 2 );
      parseDate( "in 1 jahr und 2 monaten@7",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 7,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void date_in_1_week()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.add( Calendar.WEEK_OF_YEAR, 1 );
      
      parseDate( "1 woche",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_in_year_month_at_time1()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.add( Calendar.YEAR, 1 );
      cal.add( Calendar.MONTH, 2 );
      parseDate( "in 1 jahr und 2 monaten um 7",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 7,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void date_next_weekday_time()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
      
      final MolokoCalendar now = DateTimeTestHelper.getDateParserCalendar();
      
      if ( cal.get( Calendar.DAY_OF_YEAR ) <= now.get( Calendar.DAY_OF_YEAR )
         || now.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }
      
      // due to "next" Monday
      cal.add( Calendar.WEEK_OF_YEAR, 1 );
      
      parseDate( "nächster montag 7:10",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 7,
                 10,
                 0 );
   }
   
   
   
   @Test
   public void date_on_day_month()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.MONTH, Calendar.JULY );
      cal.set( Calendar.DAY_OF_MONTH, 3 );
      
      if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
         cal.add( Calendar.YEAR, 1 );
      
      parseDate( "am 3. juli",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   @Ignore( "Parsing '2000' as time failed." )
   public void date_on_day_time()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.DAY_OF_MONTH, 21 );
      
      if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
         cal.add( Calendar.MONTH, 1 );
      
      parseDate( "am 21. 2000",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 20,
                 00,
                 00 );
   }
   
   
   
   @Test
   public void date_on_month_day_year()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2000 );
      cal.set( Calendar.MONTH, Calendar.JULY );
      cal.set( Calendar.DAY_OF_MONTH, 3 );
      
      parseDate( "am 3. juli 2000",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_on_monthday()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.DAY_OF_MONTH, 21 );
      
      if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
         cal.add( Calendar.MONTH, 1 );
      
      parseDate( "am 21.",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_on_day_of_month()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.DAY_OF_MONTH, 3 );
      cal.set( Calendar.MONTH, Calendar.JUNE );
      
      if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
         cal.add( Calendar.YEAR, 1 );
      
      parseDate( "am 3. des junis",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_on_day_of_month_year()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2006 );
      cal.set( Calendar.MONTH, Calendar.FEBRUARY );
      cal.set( Calendar.DAY_OF_MONTH, 21 );
      
      parseDate( "am 21. des feb 6",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_day_month_year()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2011 );
      cal.set( Calendar.MONTH, Calendar.JULY );
      cal.set( Calendar.DAY_OF_MONTH, 1 );
      
      parseDate( "1. juli 2011",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_day_month_year_numeric()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "10.10.2010",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_month_day_numeric()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.MONTH, Calendar.DECEMBER );
      cal.set( Calendar.DAY_OF_MONTH, 24 );
      
      parseDate( "24.12.",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ) );
   }
   
   
   
   @Test
   public void date_month_day_year_numeric_time()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "10.10.2010 13:00",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 13,
                 0,
                 0 );
   }
   
   
   
   @Test
   public void date_month_day_year_numeric_time1()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "10.10.2010, 13h 15 minuten",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 13,
                 15,
                 0 );
   }
   
   
   
   @Test
   public void time_date_numeric()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "13:15, 10.10.2010",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 13,
                 15,
                 0 );
   }
   
   
   
   @Test
   public void time_am_date_numeric()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "4:10 vormittags, 10.10.2010",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 4,
                 10,
                 0 );
   }
   
   
   
   @Test
   public void time_pm__date_numeric()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "4:10 nachm., 10.10.2010",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 16,
                 10,
                 0 );
   }
   
   
   
   @Test
   public void time_date_numeric1()
   {
      final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DAY_OF_MONTH, 10 );
      
      parseDate( "4:10, 10.10.2010",
                 cal.get( Calendar.DAY_OF_MONTH ),
                 cal.get( Calendar.MONTH ),
                 cal.get( Calendar.YEAR ),
                 4,
                 10,
                 0 );
   }
   
   
   
   @Test
   public void date_within_day()
   {
      final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
      end.add( Calendar.DAY_OF_YEAR, 1 );
      
      final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
      
      parseDateWithin( "1 tag",
                       false,
                       start.get( Calendar.YEAR ),
                       start.get( Calendar.MONTH ),
                       start.get( Calendar.WEEK_OF_YEAR ),
                       start.get( Calendar.DAY_OF_YEAR ),
                       end.get( Calendar.YEAR ),
                       end.get( Calendar.MONTH ),
                       end.get( Calendar.WEEK_OF_YEAR ),
                       end.get( Calendar.DAY_OF_YEAR ) );
   }
   
   
   
   @Test
   public void date_within_day_of_now()
   {
      final MolokoCalendar end = MolokoCalendar.getInstance();
      end.add( Calendar.DAY_OF_YEAR, 1 );
      
      final MolokoCalendar start = MolokoCalendar.getInstance();
      
      parseDateWithin( "1 tag ab heute",
                       false,
                       start.get( Calendar.YEAR ),
                       start.get( Calendar.MONTH ),
                       start.get( Calendar.WEEK_OF_YEAR ),
                       start.get( Calendar.DAY_OF_YEAR ),
                       end.get( Calendar.YEAR ),
                       end.get( Calendar.MONTH ),
                       end.get( Calendar.WEEK_OF_YEAR ),
                       end.get( Calendar.DAY_OF_YEAR ) );
   }
   
   
   
   @Test
   public void date_within_day_of_tomorrow()
   {
      final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
      end.add( Calendar.DAY_OF_YEAR, 2 );
      
      final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
      start.add( Calendar.DAY_OF_YEAR, 1 );
      
      parseDateWithin( "1 tag ab morgen",
                       false,
                       start.get( Calendar.YEAR ),
                       start.get( Calendar.MONTH ),
                       start.get( Calendar.WEEK_OF_YEAR ),
                       start.get( Calendar.DAY_OF_YEAR ),
                       end.get( Calendar.YEAR ),
                       end.get( Calendar.MONTH ),
                       end.get( Calendar.WEEK_OF_YEAR ),
                       end.get( Calendar.DAY_OF_YEAR ) );
   }
   
   
   
   @Test
   public void date_within_a_day_of_tomorrow()
   {
      final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
      end.add( Calendar.DAY_OF_YEAR, 2 );
      
      final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
      start.add( Calendar.DAY_OF_YEAR, 1 );
      
      parseDateWithin( "ein tag ab morgen",
                       false,
                       start.get( Calendar.YEAR ),
                       start.get( Calendar.MONTH ),
                       start.get( Calendar.WEEK_OF_YEAR ),
                       start.get( Calendar.DAY_OF_YEAR ),
                       end.get( Calendar.YEAR ),
                       end.get( Calendar.MONTH ),
                       end.get( Calendar.WEEK_OF_YEAR ),
                       end.get( Calendar.DAY_OF_YEAR ) );
   }
   
   
   
   @Test
   public void date_within_2_days_of_today()
   {
      final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
      end.add( Calendar.DAY_OF_YEAR, 2 );
      
      final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
      
      parseDateWithin( "2 tage ab heute",
                       false,
                       start.get( Calendar.YEAR ),
                       start.get( Calendar.MONTH ),
                       start.get( Calendar.WEEK_OF_YEAR ),
                       start.get( Calendar.DAY_OF_YEAR ),
                       end.get( Calendar.YEAR ),
                       end.get( Calendar.MONTH ),
                       end.get( Calendar.WEEK_OF_YEAR ),
                       end.get( Calendar.DAY_OF_YEAR ) );
   }
   
   
   
   @Test
   public void date_within_2_weeks_of_date()
   {
      final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
      end.set( Calendar.YEAR, 2010 );
      end.set( Calendar.MONTH, Calendar.JULY );
      end.set( Calendar.DAY_OF_MONTH, 3 );
      end.add( Calendar.WEEK_OF_YEAR, -2 );
      
      final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
      start.set( Calendar.YEAR, 2010 );
      start.set( Calendar.MONTH, Calendar.JULY );
      start.set( Calendar.DAY_OF_MONTH, 3 );
      
      parseDateWithin( "2 wochen ab 3. juli 2010",
                       true,
                       start.get( Calendar.YEAR ),
                       start.get( Calendar.MONTH ),
                       start.get( Calendar.WEEK_OF_YEAR ),
                       start.get( Calendar.DAY_OF_YEAR ),
                       end.get( Calendar.YEAR ),
                       end.get( Calendar.MONTH ),
                       end.get( Calendar.WEEK_OF_YEAR ),
                       end.get( Calendar.DAY_OF_YEAR ) );
   }
}
