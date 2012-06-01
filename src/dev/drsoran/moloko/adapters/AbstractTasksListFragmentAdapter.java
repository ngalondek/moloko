/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.adapters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.sort.CompositeComparator;
import dev.drsoran.moloko.sort.SortTaskDueDate;
import dev.drsoran.moloko.sort.SortTaskName;
import dev.drsoran.moloko.sort.SortTaskPriority;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListFragmentAdapter extends
         SwitchSelectableArrayAdapter< Task >
{
   private final Context context;
   
   private final MolokoCalendar setDueDateCalendar = MolokoCalendar.getInstance();
   
   
   
   protected AbstractTasksListFragmentAdapter( Context context,
      int unselectedResourceId, int selectedResourceId, List< Task > tasks )
   {
      super( context, unselectedResourceId, selectedResourceId, tasks );
      this.context = context;
   }
   
   
   
   public void sort( int taskSort )
   {
      switch ( taskSort )
      {
         case Settings.TASK_SORT_PRIORITY:
            sort( new CompositeComparator< Task >( new SortTaskPriority() ).add( new SortTaskDueDate() ) );
            break;
         
         case Settings.TASK_SORT_DUE_DATE:
            sort( new CompositeComparator< Task >( new SortTaskDueDate() ).add( new SortTaskPriority() ) );
            break;
         
         case Settings.TASK_SORT_NAME:
            sort( new SortTaskName() );
            break;
         
         default :
            throw new IllegalArgumentException( taskSort + " is no valid sort" );
      }
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      convertView = super.getView( position, convertView, parent );
      
      defaultInitializeListItemView( position, convertView );
      
      return convertView;
   }
   
   
   
   private void defaultInitializeListItemView( int position, View convertView )
   {
      final ImageView completed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_check );
      final TextView description = (TextView) convertView.findViewById( R.id.taskslist_listitem_desc );
      final TextView dueDate = (TextView) convertView.findViewById( R.id.taskslist_listitem_due_date );
      
      final Task task = getItem( position );
      
      UIUtils.setTaskDescription( description, task, MolokoDateUtils.newTime() );
      setDueDate( dueDate, task );
      setCompleted( completed, task );
   }
   
   
   
   private void setDueDate( TextView view, Task task )
   {
      final Date dateToSet = task.getDue();
      
      if ( dateToSet != null )
         setDueDateCalendar.setTime( dateToSet );
      
      setDueDateCalendar.setHasDate( dateToSet != null );
      setDueDateCalendar.setHasTime( task.hasDueTime() );
      
      setDueDate( view, setDueDateCalendar );
   }
   
   
   
   private void setDueDate( TextView view, MolokoCalendar cal )
   {
      // if has a due date
      if ( cal.hasDate() )
      {
         view.setVisibility( View.VISIBLE );
         
         String dueText = null;
         
         final long dueMillis = cal.getTimeInMillis();
         final boolean hasDueTime = cal.hasTime();
         
         // Today
         if ( MolokoDateUtils.isToday( dueMillis ) )
         {
            // If it has a time, we show the time
            if ( hasDueTime )
               dueText = MolokoDateUtils.formatTime( context, dueMillis );
            else
               // We only show the 'Today' phrase
               dueText = context.getString( R.string.phr_today );
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
                     dueText = MolokoDateUtils.formatDate( context,
                                                           dueMillis,
                                                           MolokoDateUtils.FORMAT_ABR_MONTH );
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
                     dueText = MolokoDateUtils.formatDate( context,
                                                           dueMillis,
                                                           MolokoDateUtils.FORMAT_ABR_MONTH );
                  }
               }
               
            }
            
            // Not the same year
            else
            {
               // we show the full date with year
               dueText = MolokoDateUtils.formatDate( context,
                                                     dueMillis,
                                                     MolokoDateUtils.FORMAT_ABR_MONTH
                                                        | MolokoDateUtils.FORMAT_WITH_YEAR );
            }
         }
         
         view.setText( dueText );
      }
      
      // has no due date
      else
         view.setVisibility( View.GONE );
   }
   
   
   
   private void setCompleted( ImageView view, Task task )
   {
      view.setEnabled( task.getCompleted() != null );
   }
}
