import java.util.Calendar;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParserTestCase_de
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
   
   
   
   private static void parseDate( String string,
                                  int d,
                                  int m,
                                  int y,
                                  int h,
                                  int min,
                                  int s,
                                  boolean hasDate,
                                  boolean hasTime )
   {
      DateTimeTestHelper.parseDate( DATE_FORMAT_CONTEXT,
                                    string,
                                    d,
                                    m,
                                    y,
                                    h,
                                    min,
                                    s,
                                    hasDate,
                                    hasTime );
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
         parseDate( "jetzt",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    cal.get( Calendar.HOUR_OF_DAY ),
                    cal.get( Calendar.MINUTE ),
                    cal.get( Calendar.SECOND ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         parseDate( "heute",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
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
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.setTimeInMillis( System.currentTimeMillis() );
         cal.roll( Calendar.HOUR_OF_DAY, 1 );
         
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
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.setTimeInMillis( System.currentTimeMillis() );
         
         final int hour_24 = cal.get( Calendar.HOUR_OF_DAY );
         cal.roll( Calendar.DAY_OF_WEEK, true );
         
         final String time = String.format( "%d:00", hour_24 );
         
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
         parseDate( "morgen@9",
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
         parseDate( "ende des monats",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.YEAR, true );
         cal.add( Calendar.MONTH, 2 );
         parseDate( "in 1 jahr und 2 monaten@7",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    7,
                    0,
                    0 );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.YEAR, true );
         cal.add( Calendar.MONTH, 2 );
         parseDate( "in 1 jahr und 2 monaten um 7",
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
         
         parseDate( "nächster montag 7:10",
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
         
         parseDate( "am 3. juli",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
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
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.MONTH, true );
         
         parseDate( "am 21.",
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
         
         parseDate( "am 3. des junis",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
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
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.MONTH, Calendar.DECEMBER );
         cal.set( Calendar.DAY_OF_MONTH, 24 );
         
         parseDate( "24.12.",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
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
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "13:15 10.10.2010",
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
         
         parseDate( "4:10 vormittags, 10.10.2010",
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
         
         parseDate( "4:10 nachm., 10.10.2010",
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
         
         parseDate( "4:10, 10.10.2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    4,
                    10,
                    0 );
      }
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
      // {
      // final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
      // cal.set( Calendar.DAY_OF_MONTH, 21 );
      //
      // if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
      // cal.roll( Calendar.MONTH, true );
      //
      // parseDate( "am 21. 2000",
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
   
}
