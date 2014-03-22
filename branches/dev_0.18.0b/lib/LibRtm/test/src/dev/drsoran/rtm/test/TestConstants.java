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

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.model.RtmConstants;


public final class TestConstants
{
   public final static long NOW = System.currentTimeMillis();
   
   public final static long TODAY;
   
   public final static RtmCalendar DATE_TODAY;
   
   public final static RtmCalendar DATE_NOW;
   
   public final static long TOMORROW;
   
   public final static RtmCalendar DATE_TOMORROW;
   
   public final static long YESTERDAY;
   
   public final static RtmCalendar DATE_YESTERDAY;
   
   public final static long BEFORE_A_WEEK;
   
   public final static RtmCalendar DATE_BEFORE_A_WEEK;
   
   public final static long LATER = NOW + 3600 * 1000;
   
   public final static long EVEN_LATER = LATER + 3600 * 1000;
   
   public final static long NEVER = RtmConstants.NO_TIME;
   
   public final static long NO_ID = -1L;
   
   public final static String RTM_NO_ID = RtmConstants.NO_ID;
   
   public static final long SECOND_IN_MILLIS = 1000;
   
   public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
   
   public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
   
   public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
   
   public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
   
   static
   {
      DATE_TODAY = RtmCalendar.getInstance();
      DATE_TODAY.setHasTime( false );
      
      DATE_NOW = RtmCalendar.getInstance();
      DATE_NOW.setTimeInMillis( NOW );
      
      TODAY = DATE_TODAY.getTimeInMillis();
      
      DATE_TOMORROW = DATE_TODAY.clone();
      DATE_TOMORROW.add( Calendar.DATE, 1 );
      
      TOMORROW = DATE_TOMORROW.getTimeInMillis();
      
      DATE_YESTERDAY = DATE_TODAY.clone();
      DATE_YESTERDAY.add( Calendar.DATE, -1 );
      
      YESTERDAY = DATE_YESTERDAY.getTimeInMillis();
      
      DATE_BEFORE_A_WEEK = DATE_TODAY.clone();
      DATE_BEFORE_A_WEEK.add( Calendar.WEEK_OF_YEAR, -1 );
      
      BEFORE_A_WEEK = DATE_BEFORE_A_WEEK.getTimeInMillis();
   }
}
