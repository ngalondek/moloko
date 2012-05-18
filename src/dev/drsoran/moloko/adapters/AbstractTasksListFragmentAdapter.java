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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListFragmentAdapter extends
         SelectableArrayAdapter< Task >
{
   private final Context context;
   
   private final int unselectedResourceId;
   
   private final int selectedResourceId;
   
   private final LayoutInflater inflater;
   
   private final MolokoCalendar setDueDateCalendar = MolokoCalendar.getInstance();
   
   private boolean isCheckable;
   
   
   
   protected AbstractTasksListFragmentAdapter( Context context,
      int unselectedResourceId, int selectedResourceId, List< Task > tasks )
   {
      super( context, View.NO_ID, tasks );
      
      this.context = context;
      this.unselectedResourceId = unselectedResourceId;
      this.selectedResourceId = selectedResourceId;
      this.inflater = ( (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) );
   }
   
   
   
   public LayoutInflater getInflater()
   {
      return inflater;
   }
   
   
   
   public boolean isCheckable()
   {
      return isCheckable;
   }
   
   
   
   public void setCheckable( boolean isCheckable )
   {
      this.isCheckable = isCheckable;
      notifyDataSetChanged();
   }
   
   
   
   public int getUnselectedLayoutRessource()
   {
      return unselectedResourceId;
   }
   
   
   
   public int getSelectedLayoutRessource()
   {
      return selectedResourceId;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null || mustSwitchLayout( convertView ) )
      {
         convertView = createListItemView( parent );
      }
      
      defaultInitializeListItemView( position, convertView );
      
      return convertView;
   }
   
   
   
   private View createListItemView( ViewGroup parent )
   {
      if ( isCheckable )
      {
         return inflater.inflate( selectedResourceId, parent, false );
      }
      else
      {
         return inflater.inflate( unselectedResourceId, parent, false );
      }
   }
   
   
   
   private void defaultInitializeListItemView( int position, View convertView )
   {
      final View priority = convertView.findViewById( R.id.taskslist_listitem_priority );
      
      final ImageView completed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_check );
      final TextView description = (TextView) convertView.findViewById( R.id.taskslist_listitem_desc );
      final TextView dueDate = (TextView) convertView.findViewById( R.id.taskslist_listitem_due_date );
      
      final Task task = getItem( position );
      
      UIUtils.setTaskDescription( description, task, MolokoDateUtils.newTime() );
      
      setDueDate( dueDate, task );
      
      UIUtils.setPriorityColor( priority, task );
      
      setCompleted( completed, task );
   }
   
   
   
   private void setDueDate( TextView view, Task task )
   {
      Date dateToSet = task.getCompleted();
      
      if ( dateToSet == null )
         dateToSet = task.getDue();
      
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
                  calWeekdayRange.add( Calendar.DAY_OF_MONTH, 6 );
                  nowCal = null;
                  
                  if ( calWeekdayRange.get( Calendar.DAY_OF_YEAR ) >= cal.get( Calendar.DAY_OF_YEAR ) )
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
   
   
   
   protected abstract boolean mustSwitchLayout( View convertView );
}
