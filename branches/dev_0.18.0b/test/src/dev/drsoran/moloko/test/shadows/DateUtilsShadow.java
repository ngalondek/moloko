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

package dev.drsoran.moloko.test.shadows;

import java.util.Calendar;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import android.text.format.DateUtils;


@Implements( DateUtils.class )
abstract public class DateUtilsShadow
{
   public static final String SUNDAY = "sunday";
   
   public static final String SUN = "sun";
   
   public static final String SATURDAY = "saturday";
   
   public static final String SAT = "sat";
   
   public static final String FRIDAY = "friday";
   
   public static final String FRI = "fri";
   
   public static final String THURSDAY = "thursday";
   
   public static final String THU = "thu";
   
   public static final String WEDNESDAY = "wednesday";
   
   public static final String WED = "wed";
   
   public static final String TUESDAY = "tuesday";
   
   public static final String TUE = "tue";
   
   public static final String MON = "mon";
   
   public static final String MONDAY = "monday";
   
   
   
   @Implementation
   public static String getDayOfWeekString( int dayOfWeek, int abbrev )
   {
      boolean isAbbrev = abbrev != DateUtils.LENGTH_LONG;
      
      switch ( dayOfWeek )
      {
         case Calendar.MONDAY:
            return isAbbrev ? MON : MONDAY;
            
         case Calendar.TUESDAY:
            return isAbbrev ? TUE : TUESDAY;
            
         case Calendar.WEDNESDAY:
            return isAbbrev ? WED : WEDNESDAY;
            
         case Calendar.THURSDAY:
            return isAbbrev ? THU : THURSDAY;
            
         case Calendar.FRIDAY:
            return isAbbrev ? FRI : FRIDAY;
            
         case Calendar.SATURDAY:
            return isAbbrev ? SAT : SATURDAY;
            
         case Calendar.SUNDAY:
            return isAbbrev ? SUN : SUNDAY;
            
         default :
            throw new IllegalArgumentException( "dayOfWeek" );
      }
   }
}
