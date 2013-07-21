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

package dev.drsoran.moloko.domain;

import java.util.Calendar;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.IDateParserListener;


public class DefaultDateParserListener implements IDateParserListener
{
   
   @Override
   public void onParsedNumericDate( MolokoCalendar cal,
                                    String part1,
                                    String part2,
                                    String part3 )
   {
      // if year is missing and the date is
      // before now we roll to the next year.
      if ( part3 == null )
      {
         final MolokoCalendar now = MolokoCalendar.getInstance();
         
         if ( cal.before( now ) )
         {
            cal.add( Calendar.YEAR, 1 );
         }
      }
   }
   
   
   
   @Override
   public void onParsedDateOnXstOfMonth( MolokoCalendar cal,
                                         boolean hasYear,
                                         boolean hasMonth )
   {
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( !hasYear )
      {
         final MolokoCalendar now = MolokoCalendar.getInstance();
         
         if ( cal.before( now ) )
         {
            // if we have a month, we roll to next year.
            if ( hasMonth )
            {
               cal.add( Calendar.YEAR, 1 );
            }
            // if we only have a day, we roll to next month.
            else
            {
               cal.add( Calendar.MONTH, 1 );
            }
         }
      }
   }
   
   
   
   @Override
   public void onParsedDateOnWeekday( MolokoCalendar cal,
                                      int weekday,
                                      boolean nextWeek )
   {
      final MolokoCalendar now = MolokoCalendar.getInstance();
      final int currentWeekDay = now.get( Calendar.DAY_OF_WEEK );
      
      // If:
      // - the weekday is before today or today,
      // - today is sunday
      // we adjust to next week.
      if ( weekday <= currentWeekDay || currentWeekDay == Calendar.SUNDAY )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }
      
      // if the next week is explicitly enforced
      if ( nextWeek )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }
   }
   
}
