import java.util.Calendar;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParserTestCase_en
{
   private static IDateFormatContext DATE_FORMAT_CONTEXT;
   
   
   
   private static void parseDate( String string, int d, int m, int y )
   {
      parseDate( string, d, m, y, -1, -1, -1 );
   }
   
   
   
   private static void parseDate( String string,
                                  int d,
                                  int m,
                                  int y,
                                  int h,
                                  int min,
                                  int s )
   {
      DateTimeTestHelper.parseDate( DATE_FORMAT_CONTEXT,
                                    string,
                                    d,
                                    m,
                                    y,
                                    h,
                                    min,
                                    s );
   }
   
   
   
   private static void parseDateWithin( String string,
                                        boolean past,
                                        int sy,
                                        int sm,
                                        int sw,
                                        int sd,
                                        int ey,
                                        int em,
                                        int ew,
                                        int ed )
   {
      DateTimeTestHelper.parseDateWithin( DATE_FORMAT_CONTEXT,
                                          string,
                                          past,
                                          sy,
                                          sm,
                                          sw,
                                          sd,
                                          ey,
                                          em,
                                          ew,
                                          ed );
   }
   
   
   
   public final static void execute( IDateFormatContext dateFormatContext )
   {
      DATE_FORMAT_CONTEXT = dateFormatContext;
      
      {
         final Calendar cal = Calendar.getInstance();
         parseDate( "now",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    cal.get( Calendar.HOUR_OF_DAY ),
                    cal.get( Calendar.MINUTE ),
                    cal.get( Calendar.SECOND ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         parseDate( "today",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.setTimeInMillis( System.currentTimeMillis() );
         cal.roll( Calendar.HOUR_OF_DAY, 1 );
         
         final int hour_24 = cal.get( Calendar.HOUR_OF_DAY );
         final int hour_12 = cal.get( Calendar.HOUR );
         final String time = String.format( "%d%s",
                                            hour_12,
                                            cal.get( Calendar.AM_PM ) == Calendar.AM
                                                                                    ? "am"
                                                                                    : "pm" );
         
         parseDate( time,
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    hour_24,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.setTimeInMillis( System.currentTimeMillis() );
         
         final int hour_24 = cal.get( Calendar.HOUR_OF_DAY );
         final int hour_12 = cal.get( Calendar.HOUR );
         cal.roll( Calendar.DAY_OF_WEEK, true );
         
         final String time = String.format( "%d%s",
                                            hour_12,
                                            cal.get( Calendar.AM_PM ) == Calendar.AM
                                                                                    ? "am"
                                                                                    : "pm" );
         
         parseDate( time,
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    hour_24,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         parseDate( "tomorrow@9",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    9,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH,
                  cal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
         parseDate( "end of month",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.YEAR, true );
         cal.add( Calendar.MONTH, 2 );
         parseDate( "in 1 year and 2 mons@7",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    7,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
         
         final MolokoCalendar now = DateTimeTestHelper.getDateParserCalendar();
         
         if ( cal.get( Calendar.DAY_OF_YEAR ) <= now.get( Calendar.DAY_OF_YEAR )
            || now.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY )
            cal.roll( Calendar.WEEK_OF_YEAR, true );
         
         // due to "next" Monday
         cal.roll( Calendar.WEEK_OF_YEAR, true );
         
         parseDate( "next monday 7:10",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    7,
                    10,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.YEAR, true );
         
         parseDate( "on july 3rd",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2000 );
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         
         parseDate( "on july 3rd 2000",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.MONTH, true );
         
         parseDate( "on 21st",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         cal.set( Calendar.MONTH, Calendar.JUNE );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.YEAR, true );
         
         parseDate( "on 3rd of june",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2006 );
         cal.set( Calendar.MONTH, Calendar.FEBRUARY );
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         parseDate( "on 21st of feb 6",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2011 );
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 1 );
         
         parseDate( "1-july-2011",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10/10/2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.JANUARY );
         cal.set( Calendar.DAY_OF_MONTH, 13 );
         
         // M/d/y
         parseDate( "1/13/2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.MONTH, Calendar.DECEMBER );
         cal.set( Calendar.DAY_OF_MONTH, 24 );
         
         parseDate( "12/24",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10/10/2010 1 PM",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10/10/2010, 13h 15 minutes",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    15,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "13:15 10/10/2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    15,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "4:10am, 10/10/2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    4,
                    10,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "4:10p, 10/10/2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    16,
                    10,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "4:10, 10/10/2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    4,
                    10,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         parseDate( "tom@12",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    12,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         parseDate( "tod@1310",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    10,
                    0 );
      }
      // {
      // final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      // cal.set( Calendar.DAY_OF_MONTH, 21 );
      //
      // if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
      // cal.roll( Calendar.MONTH, true );
      //
      // parseDate( "on 21st 2000",
      // cal.get( Calendar.DAY_OF_MONTH ),
      // cal.get( Calendar.MONTH ),
      // cal.get( Calendar.YEAR ),
      // 20,
      // 00,
      // 00 );
      // }
      
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.roll( Calendar.DAY_OF_YEAR, true );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         
         parseDateWithin( "1 day",
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
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.add( Calendar.DAY_OF_YEAR, 2 );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         start.add( Calendar.DAY_OF_YEAR, 1 );
         
         parseDateWithin( "1 day of tomorrow",
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
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.add( Calendar.DAY_OF_YEAR, 2 );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         start.add( Calendar.DAY_OF_YEAR, 1 );
         
         parseDateWithin( "a day of tomorrow",
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
         
         parseDateWithin( "2 weeks of july-3rd 2010",
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
   
}
