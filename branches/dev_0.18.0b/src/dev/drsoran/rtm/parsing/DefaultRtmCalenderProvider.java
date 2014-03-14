/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.parsing;

import android.text.format.DateUtils;
import dev.drsoran.rtm.RtmCalendar;


public class DefaultRtmCalenderProvider implements IRtmCalendarProvider
{
   @Override
   public RtmCalendar getNow()
   {
      return RtmCalendar.getInstance();
   }
   
   
   
   @Override
   public long getNowMillisUtc()
   {
      return System.currentTimeMillis();
   }
   
   
   
   @Override
   public RtmCalendar getToday()
   {
      final RtmCalendar cal = RtmCalendar.getInstance();
      cal.setHasTime( false );
      
      return cal;
   }
   
   
   
   @Override
   public long getTodayMillisUtc()
   {
      return ( getNowMillisUtc() / DateUtils.DAY_IN_MILLIS )
         * DateUtils.DAY_IN_MILLIS;
   }
}
