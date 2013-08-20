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

package dev.drsoran.moloko.test;

import java.util.Calendar;

import org.easymock.EasyMock;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;


public class TestCalendarProvider
{
   
   private final static MolokoCalendar TODAY_CAL;
   
   static
   {
      TODAY_CAL = MolokoCalendar.getInstance();
      TODAY_CAL.set( Calendar.YEAR, 2010 );
      TODAY_CAL.set( Calendar.MONTH, Calendar.JUNE );
      TODAY_CAL.set( Calendar.DATE, 10 );
      TODAY_CAL.setHasTime( false );
   }
   
   
   
   public static MolokoCalenderProvider get( MolokoCalendar now,
                                             MolokoCalendar today )
   {
      final MolokoCalenderProvider calenderProvider = EasyMock.createNiceMock( MolokoCalenderProvider.class );
      EasyMock.expect( calenderProvider.getNow() )
              .andReturn( now.clone() )
              .anyTimes();
      EasyMock.expect( calenderProvider.getToday() )
              .andReturn( today.clone() )
              .anyTimes();
      EasyMock.replay( calenderProvider );
      
      return calenderProvider;
   }
   
   
   
   public static MolokoCalenderProvider getDefault()
   {
      return get( TODAY_CAL, TODAY_CAL );
   }
}
