/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.unit.util;

import static dev.drsoran.moloko.test.TestConstants.DATE_NOW;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.robolectric.annotation.Config;

import android.text.format.DateUtils;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.TimeStruct;
import dev.drsoran.rtm.RtmCalendar;


@Config( manifest = Config.NONE )
public class MolokoDateUtilsFixture extends MolokoRoboTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( MolokoDateUtils.class );
   }
   
   
   
   @Test
   public void testNewCalendar()
   {
      assertThat( MolokoDateUtils.newCalendar( NOW ).getTimeInMillis(),
                  is( NOW ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewCalendarNegMillis()
   {
      MolokoDateUtils.newCalendar( -1 );
   }
   
   
   
   @Test
   public void testIsToday()
   {
      final RtmCalendar cal = DATE_NOW.clone();
      
      assertTrue( MolokoDateUtils.isToday( DATE_NOW.getTimeInMillis(),
                                           cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      assertFalse( MolokoDateUtils.isToday( DATE_NOW.getTimeInMillis(),
                                            cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertFalse( MolokoDateUtils.isToday( DATE_NOW.getTimeInMillis(),
                                            cal.getTimeInMillis() ) );
   }
   
   
   
   @Test
   public void testIsTodayNegMillis()
   {
      assertThat( MolokoDateUtils.isToday( DATE_NOW.getTimeInMillis(), -10 ),
                  is( false ) );
   }
   
   
   
   @Test
   public void testIsDaysBefore()
   {
      final Calendar cal = Calendar.getInstance();
      assertFalse( MolokoDateUtils.isDaysBefore( cal.getTimeInMillis(),
                                                 cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      assertFalse( MolokoDateUtils.isDaysBefore( cal.getTimeInMillis(), NOW ) );
      assertTrue( MolokoDateUtils.isDaysBefore( NOW, cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertTrue( MolokoDateUtils.isDaysBefore( cal.getTimeInMillis(), NOW ) );
      assertFalse( MolokoDateUtils.isDaysBefore( NOW, cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertTrue( MolokoDateUtils.isDaysBefore( cal.getTimeInMillis(), NOW ) );
      assertFalse( MolokoDateUtils.isDaysBefore( NOW, cal.getTimeInMillis() ) );
   }
   
   
   
   @Test
   public void testIsDaysBeforeNegWhen()
   {
      assertThat( MolokoDateUtils.isDaysBefore( -1, NOW ), is( true ) );
   }
   
   
   
   @Test
   public void testIsDaysBeforeNegReference()
   {
      assertThat( MolokoDateUtils.isDaysBefore( NOW, -1 ), is( false ) );
   }
   
   
   
   @Test
   public void testIsDaysAfter()
   {
      final Calendar cal = Calendar.getInstance();
      assertFalse( MolokoDateUtils.isDaysAfter( cal.getTimeInMillis(),
                                                cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      assertTrue( MolokoDateUtils.isDaysAfter( cal.getTimeInMillis(), NOW ) );
      assertFalse( MolokoDateUtils.isDaysAfter( NOW, cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertFalse( MolokoDateUtils.isDaysAfter( cal.getTimeInMillis(), NOW ) );
      assertTrue( MolokoDateUtils.isDaysAfter( NOW, cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertFalse( MolokoDateUtils.isDaysAfter( cal.getTimeInMillis(), NOW ) );
      assertTrue( MolokoDateUtils.isDaysAfter( NOW, cal.getTimeInMillis() ) );
   }
   
   
   
   @Test
   public void testIsDaysAfterNegWhen()
   {
      assertThat( MolokoDateUtils.isDaysAfter( -1, NOW ), is( false ) );
   }
   
   
   
   @Test
   public void testIsDaysAfterNegReference()
   {
      assertThat( MolokoDateUtils.isDaysAfter( NOW, -1 ), is( true ) );
   }
   
   
   
   @Test
   public void testGetTimespanInDays()
   {
      final Calendar cal = Calendar.getInstance();
      assertThat( MolokoDateUtils.getTimespanInDays( cal.getTimeInMillis(),
                                                     cal.getTimeInMillis() ),
                  is( 0 ) );
      
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      assertThat( MolokoDateUtils.getTimespanInDays( cal.getTimeInMillis(), NOW ),
                  is( -1 ) );
      assertThat( MolokoDateUtils.getTimespanInDays( NOW, cal.getTimeInMillis() ),
                  is( 1 ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertThat( MolokoDateUtils.getTimespanInDays( cal.getTimeInMillis(), NOW ),
                  is( 1 ) );
      assertThat( MolokoDateUtils.getTimespanInDays( NOW, cal.getTimeInMillis() ),
                  is( -1 ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertThat( MolokoDateUtils.getTimespanInDays( cal.getTimeInMillis(), NOW ),
                  is( 3 ) );
      assertThat( MolokoDateUtils.getTimespanInDays( NOW, cal.getTimeInMillis() ),
                  is( -3 ) );
   }
   
   
   
   @Test
   public void testGetTimespanInDaysNegStart()
   {
      assertThat( MolokoDateUtils.getTimespanInDays( -1, NOW ), is( 16149 ) );
   }
   
   
   
   @Test
   public void testGetTimespanInDaysNegEnd()
   {
      assertThat( MolokoDateUtils.getTimespanInDays( NOW, -1 ), is( -16149 ) );
   }
   
   
   
   @Test
   public void testGetFittingDateUtilsResolution()
   {
      final Calendar cal1 = Calendar.getInstance();
      final Calendar cal2 = Calendar.getInstance();
      
      // Second
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal1.getTimeInMillis(),
                                                                 cal1.getTimeInMillis() ),
                  is( DateUtils.SECOND_IN_MILLIS ) );
      
      cal1.add( Calendar.SECOND, 10 );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal1.getTimeInMillis(),
                                                                 cal2.getTimeInMillis() ),
                  is( DateUtils.SECOND_IN_MILLIS ) );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal2.getTimeInMillis(),
                                                                 cal1.getTimeInMillis() ),
                  is( DateUtils.SECOND_IN_MILLIS ) );
      cal1.setTimeInMillis( cal2.getTimeInMillis() );
      
      // Minute
      cal1.add( Calendar.MINUTE, 1 );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal1.getTimeInMillis(),
                                                                 cal2.getTimeInMillis() ),
                  is( DateUtils.MINUTE_IN_MILLIS ) );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal2.getTimeInMillis(),
                                                                 cal1.getTimeInMillis() ),
                  is( DateUtils.MINUTE_IN_MILLIS ) );
      cal1.setTimeInMillis( cal2.getTimeInMillis() );
      
      // Hour
      cal1.add( Calendar.HOUR, -1 );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal1.getTimeInMillis(),
                                                                 cal2.getTimeInMillis() ),
                  is( DateUtils.HOUR_IN_MILLIS ) );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal2.getTimeInMillis(),
                                                                 cal1.getTimeInMillis() ),
                  is( DateUtils.HOUR_IN_MILLIS ) );
      cal1.setTimeInMillis( cal2.getTimeInMillis() );
      
      // Day
      cal1.add( Calendar.HOUR, 25 );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal1.getTimeInMillis(),
                                                                 cal2.getTimeInMillis() ),
                  is( DateUtils.DAY_IN_MILLIS ) );
      assertThat( MolokoDateUtils.getFittingDateUtilsResolution( cal2.getTimeInMillis(),
                                                                 cal1.getTimeInMillis() ),
                  is( DateUtils.DAY_IN_MILLIS ) );
      cal1.setTimeInMillis( cal2.getTimeInMillis() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetFittingDateUtilsResolutionNegMillis()
   {
      MolokoDateUtils.getFittingDateUtilsResolution( -1, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetFittingDateUtilsResolutionNegNowMillis()
   {
      MolokoDateUtils.getFittingDateUtilsResolution( NOW, -1 );
   }
   
   
   
   @Test
   public void testGetDayOfWeekString()
   {
      final RtmCalendar cal = RtmCalendar.getInstance();
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Monday" ) );
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.TUESDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Tuesday" ) );
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Wednesday" ) );
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.THURSDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Thursday" ) );
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.FRIDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Friday" ) );
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.SATURDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Saturday" ) );
      
      cal.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );
      assertThat( MolokoDateUtils.getDayOfWeekString( cal ), is( "Sunday" ) );
   }
   
   
   
   @Test
   public void testGetTimeStruct()
   {
      TimeStruct timeStruct = MolokoDateUtils.getTimeStruct( 0 );
      assertThat( timeStruct.days, is( 0 ) );
      assertThat( timeStruct.hours, is( 0 ) );
      assertThat( timeStruct.minutes, is( 0 ) );
      assertThat( timeStruct.seconds, is( 0 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 10 * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 0 ) );
      assertThat( timeStruct.hours, is( 0 ) );
      assertThat( timeStruct.minutes, is( 0 ) );
      assertThat( timeStruct.seconds, is( 10 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 60 * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 0 ) );
      assertThat( timeStruct.hours, is( 0 ) );
      assertThat( timeStruct.minutes, is( 1 ) );
      assertThat( timeStruct.seconds, is( 0 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 2
         * DateUtils.MINUTE_IN_MILLIS + 10 * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 0 ) );
      assertThat( timeStruct.hours, is( 0 ) );
      assertThat( timeStruct.minutes, is( 2 ) );
      assertThat( timeStruct.seconds, is( 10 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 59
         * DateUtils.MINUTE_IN_MILLIS + 60 * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 0 ) );
      assertThat( timeStruct.hours, is( 1 ) );
      assertThat( timeStruct.minutes, is( 0 ) );
      assertThat( timeStruct.seconds, is( 0 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 4 * DateUtils.HOUR_IN_MILLIS
         + 2 * DateUtils.MINUTE_IN_MILLIS + 10 * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 0 ) );
      assertThat( timeStruct.hours, is( 4 ) );
      assertThat( timeStruct.minutes, is( 2 ) );
      assertThat( timeStruct.seconds, is( 10 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 3 * DateUtils.DAY_IN_MILLIS
         + 4 * DateUtils.HOUR_IN_MILLIS + 2 * DateUtils.MINUTE_IN_MILLIS + 10
         * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 3 ) );
      assertThat( timeStruct.hours, is( 4 ) );
      assertThat( timeStruct.minutes, is( 2 ) );
      assertThat( timeStruct.seconds, is( 10 ) );
      
      timeStruct = MolokoDateUtils.getTimeStruct( 23 * DateUtils.HOUR_IN_MILLIS
         + 59 * DateUtils.MINUTE_IN_MILLIS + 60 * DateUtils.SECOND_IN_MILLIS );
      assertThat( timeStruct.days, is( 1 ) );
      assertThat( timeStruct.hours, is( 0 ) );
      assertThat( timeStruct.minutes, is( 0 ) );
      assertThat( timeStruct.seconds, is( 0 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetTimeStructNegMillis()
   {
      MolokoDateUtils.getTimeStruct( -1 );
   }
}
