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

package dev.drsoran.rtm.test.unit.parsing;

import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.day;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.hour;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.minute;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.month;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.second;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.year;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Test;

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.DefaultRtmCalenderProvider;


public class DefaultRtmCalenderProviderFixture
{
   @Test
   public void testGetNow()
   {
      final RtmCalendar refNow = RtmCalendar.getInstance();
      final RtmCalendar calNow = new DefaultRtmCalenderProvider().getNow();
      
      assertThat( calNow.hasDate(), is( true ) );
      assertThat( calNow.hasTime(), is( true ) );
      assertThat( calNow, is( year( refNow.get( Calendar.YEAR ) ) ) );
      assertThat( calNow, is( month( refNow.get( Calendar.MONTH ) ) ) );
      assertThat( calNow, is( day( refNow.get( Calendar.DATE ) ) ) );
   }
   
   
   
   @Test
   public void testGetToday()
   {
      final RtmCalendar refToday = RtmCalendar.getInstance();
      final RtmCalendar calToday = new DefaultRtmCalenderProvider().getToday();
      
      assertThat( calToday.hasDate(), is( true ) );
      assertThat( calToday.hasTime(), is( false ) );
      assertThat( calToday, is( year( refToday.get( Calendar.YEAR ) ) ) );
      assertThat( calToday, is( month( refToday.get( Calendar.MONTH ) ) ) );
      assertThat( calToday, is( day( refToday.get( Calendar.DATE ) ) ) );
      assertThat( calToday, is( hour( 0 ) ) );
      assertThat( calToday, is( minute( 0 ) ) );
      assertThat( calToday, is( second( 0 ) ) );
   }
}
