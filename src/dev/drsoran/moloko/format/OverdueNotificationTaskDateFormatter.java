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

import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;


public final class OverdueNotificationTaskDateFormatter
{
   private final Context context;
   
   
   
   public OverdueNotificationTaskDateFormatter( Context context )
   {
      this.context = context;
   }
   
   
   
   public String getFormattedOverdueDueDate( final long dueMillis )
   {
      String dueText = Strings.EMPTY_STRING;
      
      final long nowMillis = System.currentTimeMillis();
      final int dueDaysBefore = MolokoDateUtils.getTimespanInDays( dueMillis,
                                                                   nowMillis );
      
      // We only format for dates at least one day in the past
      if ( dueDaysBefore > 0 )
      {
         // Was it yesterday?
         if ( dueDaysBefore == 1 )
         {
            dueText = context.getString( R.string.phr_yesterday );
         }
         
         // More than one day in the past
         else
         {
            final MolokoCalendar cal = MolokoDateUtils.newCalendar( dueMillis );
            final MolokoCalendar nowCal = MolokoDateUtils.newCalendar( nowMillis );
            
            // If it is the same year
            if ( cal.get( Calendar.YEAR ) == nowCal.get( Calendar.YEAR ) )
            {
               // If the same week
               if ( nowCal.get( Calendar.WEEK_OF_YEAR ) == cal.get( Calendar.WEEK_OF_YEAR ) )
               {
                  // we show the weekday
                  dueText = MolokoDateFormatter.formatDate( context,
                                                            dueMillis,
                                                            MolokoDateFormatter.FORMAT_SHOW_WEEKDAY
                                                               | MolokoDateFormatter.FORMAT_ABR_MONTH );
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
