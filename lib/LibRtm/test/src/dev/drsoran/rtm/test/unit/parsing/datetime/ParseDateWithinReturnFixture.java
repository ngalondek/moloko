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

package dev.drsoran.rtm.test.unit.parsing.datetime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.rtm.test.TestConstants;


public class ParseDateWithinReturnFixture
{
   @Test
   public void testParseDateWithinReturn()
   {
      final RtmCalendar cal1 = RtmCalendar.getInstance();
      cal1.setTimeInMillis( TestConstants.NOW );
      
      final RtmCalendar cal2 = RtmCalendar.getInstance();
      cal2.setTimeInMillis( TestConstants.LATER );
      
      new ParseDateWithinReturn( cal1, cal2 );
   }
   
   
   
   @Test
   public void testStartEpoch()
   {
      final RtmCalendar cal1 = RtmCalendar.getInstance();
      cal1.setTimeInMillis( TestConstants.NOW );
      
      final RtmCalendar cal2 = RtmCalendar.getInstance();
      cal2.setTimeInMillis( TestConstants.LATER );
      
      assertThat( new ParseDateWithinReturn( cal1, cal2 ).startEpoch.getTimeInMillis(),
                  is( TestConstants.NOW ) );
   }
   
   
   
   @Test
   public void testEndEpoch()
   {
      final RtmCalendar cal1 = RtmCalendar.getInstance();
      cal1.setTimeInMillis( TestConstants.NOW );
      
      final RtmCalendar cal2 = RtmCalendar.getInstance();
      cal2.setTimeInMillis( TestConstants.LATER );
      
      assertThat( new ParseDateWithinReturn( cal1, cal2 ).endEpoch.getTimeInMillis(),
                  is( TestConstants.LATER ) );
   }
}
