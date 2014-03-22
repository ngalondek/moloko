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

package dev.drsoran.rtm.test;

import java.util.Calendar;

import org.easymock.EasyMock;

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;


public class TestCalendarProvider
{
   private final static RtmCalendar TODAY_CAL;
   
   static
   {
      // Thursday, 10.6.2010
      TODAY_CAL = RtmCalendar.getInstance();
      TODAY_CAL.set( Calendar.YEAR, 2010 );
      TODAY_CAL.set( Calendar.MONTH, Calendar.JUNE );
      TODAY_CAL.set( Calendar.DATE, 10 );
      TODAY_CAL.setHasTime( false );
   }
   
   
   
   private TestCalendarProvider()
   {
   }
   
   
   
   public static IRtmCalendarProvider get()
   {
      return get( TestConstants.DATE_NOW, TestConstants.DATE_TODAY );
   }
   
   
   
   public static IRtmCalendarProvider get( RtmCalendar now, RtmCalendar today )
   {
      final IRtmCalendarProvider calenderProvider = EasyMock.createNiceMock( IRtmCalendarProvider.class );
      EasyMock.expect( calenderProvider.getNow() )
              .andReturn( now.clone() )
              .anyTimes();
      EasyMock.expect( calenderProvider.getNowMillisUtc() )
              .andReturn( now.getTimeInMillis() )
              .anyTimes();
      EasyMock.expect( calenderProvider.getToday() )
              .andReturn( today.clone() )
              .anyTimes();
      EasyMock.expect( calenderProvider.getTodayMillisUtc() )
              .andReturn( today.getTimeInMillis() )
              .anyTimes();
      EasyMock.replay( calenderProvider );
      
      return calenderProvider;
   }
   
   
   
   public static IRtmCalendarProvider getJune_10_2010_00_00_00()
   {
      final RtmCalendar nowCal = TODAY_CAL.clone();
      nowCal.setHasTime( true );
      
      return get( nowCal, TODAY_CAL );
   }
}
