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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.robolectric.annotation.Config;

import android.text.format.DateUtils;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.shadows.DateUtilsShadow;
import dev.drsoran.moloko.test.unit.PrivateCtorCaller;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.TimeStruct;


@Config( manifest = Config.NONE, shadows =
{ DateUtilsShadow.class } )
public class MolokoDateUtilsFixture extends MolokoRoboTestCase
{
   private final static long NOW = System.currentTimeMillis();
   
   
   
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
   public void testNewTime()
   {
      final long nowMillis = ( System.currentTimeMillis() / 1000 ) * 1000;
      final long newTimeMillis = ( MolokoDateUtils.newTime().toMillis( true ) / 1000 ) * 1000;
      assertThat( newTimeMillis, is( nowMillis ) );
   }
   
   
   
   @Test
   public void testNewTimeLong()
   {
      assertThat( MolokoDateUtils.newTime( NOW ).toMillis( true ),
                  is( ( NOW / 1000 ) * 1000 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewTimeLongNegMillis()
   {
      MolokoDateUtils.newTime( -1 );
   }
   
   
   
   @Test
   public void testIsToday()
   {
      final Calendar cal = Calendar.getInstance();
      assertTrue( MolokoDateUtils.isToday( cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      assertFalse( MolokoDateUtils.isToday( cal.getTimeInMillis() ) );
      
      cal.add( Calendar.DAY_OF_YEAR, -2 );
      assertFalse( MolokoDateUtils.isToday( cal.getTimeInMillis() ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testIsTodayNegMillis()
   {
      MolokoDateUtils.isToday( -10 );
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
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testIsDaysBeforeNegWhen()
   {
      MolokoDateUtils.isDaysBefore( -1, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testIsDaysBeforeNegReference()
   {
      MolokoDateUtils.isDaysBefore( NOW, -1 );
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
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testIsDaysAfterNegWhen()
   {
      MolokoDateUtils.isDaysAfter( -1, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testIsDaysAfterNegReference()
   {
      MolokoDateUtils.isDaysAfter( NOW, -1 );
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
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetTimespanInDaysNegStart()
   {
      MolokoDateUtils.getTimespanInDays( -1, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetTimespanInDaysNegEnd()
   {
      MolokoDateUtils.getTimespanInDays( NOW, -1 );
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
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.MONDAY ),
                  is( DateUtilsShadow.MONDAY ) );
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.TUESDAY ),
                  is( DateUtilsShadow.TUESDAY ) );
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.WEDNESDAY ),
                  is( DateUtilsShadow.WEDNESDAY ) );
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.THURSDAY ),
                  is( DateUtilsShadow.THURSDAY ) );
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.FRIDAY ),
                  is( DateUtilsShadow.FRIDAY ) );
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.SATURDAY ),
                  is( DateUtilsShadow.SATURDAY ) );
      assertThat( MolokoDateUtils.getDayOfWeekString( Calendar.SUNDAY ),
                  is( DateUtilsShadow.SUNDAY ) );
   }
   
   
   
   @Test
   public void testGetAbbreviatedDayOfWeekString()
   {
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.MONDAY ),
                  is( DateUtilsShadow.MON ) );
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.TUESDAY ),
                  is( DateUtilsShadow.TUE ) );
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.WEDNESDAY ),
                  is( DateUtilsShadow.WED ) );
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.THURSDAY ),
                  is( DateUtilsShadow.THU ) );
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.FRIDAY ),
                  is( DateUtilsShadow.FRI ) );
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.SATURDAY ),
                  is( DateUtilsShadow.SAT ) );
      assertThat( MolokoDateUtils.getAbbreviatedDayOfWeekString( Calendar.SUNDAY ),
                  is( DateUtilsShadow.SUN ) );
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
