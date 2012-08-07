/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.format;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.Task;


public final class RtmStyleTaskDateFormatter
{
   private final MolokoCalendar dueDateCalender = MolokoCalendar.getInstance();
   
   private final Context context;
   
   
   
   public RtmStyleTaskDateFormatter( Context context )
   {
      this.context = context;
   }
   
   
   
   public String getFormattedDueDate( Task task )
   {
      final Date dateToSet = task.getDue();
      
      if ( dateToSet != null )
         dueDateCalender.setTime( dateToSet );
      
      dueDateCalender.setHasDate( dateToSet != null );
      dueDateCalender.setHasTime( task.hasDueTime() );
      
      return getFormattedDueDate( dueDateCalender );
   }
   
   
   
   public String getFormattedDueDate( MolokoCalendar cal )
   {
      String dueText = Strings.EMPTY_STRING;
      
      // if has a due date
      if ( cal.hasDate() )
      {
         final long dueMillis = cal.getTimeInMillis();
         final boolean hasDueTime = cal.hasTime();
         
         // Today
         if ( MolokoDateUtils.isToday( dueMillis ) )
         {
            // If it has a time, we show the time
            if ( hasDueTime )
            {
               dueText = MolokoDateFormatter.formatTime( context, dueMillis );
            }
            else
            {
               // We only show the 'Today' phrase
               dueText = context.getString( R.string.phr_today );
            }
         }
         
         // Not today
         else
         {
            final long nowMillis = System.currentTimeMillis();
            MolokoCalendar nowCal = MolokoDateUtils.newCalendar( nowMillis );
            
            // If it is the same year
            if ( cal.get( Calendar.YEAR ) == nowCal.get( Calendar.YEAR ) )
            {
               // If the same week
               if ( nowCal.get( Calendar.WEEK_OF_YEAR ) == cal.get( Calendar.WEEK_OF_YEAR ) )
               {
                  // is in the past?
                  if ( cal.before( nowCal ) )
                  {
                     // we show the date but w/o year
                     dueText = MolokoDateFormatter.formatDate( context,
                                                               dueMillis,
                                                               MolokoDateFormatter.FORMAT_ABR_MONTH );
                  }
                  
                  // later this week
                  else
                  {
                     // we only show the week day
                     dueText = MolokoDateUtils.getDayOfWeekString( cal.get( Calendar.DAY_OF_WEEK ),
                                                                   false );
                  }
               }
               
               // Is it in the range [today + 1, today + 6]?
               else
               {
                  final MolokoCalendar calWeekdayRange = nowCal;
                  calWeekdayRange.add( Calendar.DAY_OF_MONTH, 1 );
                  nowCal = null;
                  
                  final int calDayOfYear = cal.get( Calendar.DAY_OF_YEAR );
                  boolean isInNext6DaysRange = calWeekdayRange.get( Calendar.DAY_OF_YEAR ) <= calDayOfYear;
                  
                  if ( isInNext6DaysRange )
                  {
                     calWeekdayRange.add( Calendar.DAY_OF_MONTH, 5 );
                     isInNext6DaysRange = calWeekdayRange.get( Calendar.DAY_OF_YEAR ) >= calDayOfYear;
                  }
                  
                  if ( isInNext6DaysRange )
                  {
                     // we only show the week day
                     dueText = MolokoDateUtils.getDayOfWeekString( cal.get( Calendar.DAY_OF_WEEK ),
                                                                   false );
                  }
                  // Not the same week and not in the range [today + 1, today + 6]
                  else
                  {
                     // we show the date but w/o year
                     dueText = MolokoDateFormatter.formatDate( context,
                                                               dueMillis,
                                                               MolokoDateFormatter.FORMAT_ABR_MONTH );
                  }
               }
               
            }
            
            // Not the same year
            else
            {
               // we show the full date with year
               dueText = MolokoDateFormatter.formatDate( context,
                                                         dueMillis,
                                                         MolokoDateFormatter.FORMAT_ABR_MONTH
                                                            | MolokoDateFormatter.FORMAT_WITH_YEAR );
            }
         }
      }
      
      return dueText;
   }
}
