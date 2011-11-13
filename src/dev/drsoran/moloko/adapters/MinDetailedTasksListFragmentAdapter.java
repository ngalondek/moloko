/*
 * Copyright (c) 2011 Ronny Röhricht
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class MinDetailedTasksListFragmentAdapter extends ArrayAdapter< Task >
{
   private final static String TAG = "Moloko."
      + MinDetailedTasksListFragmentAdapter.class.getName();
   
   private final Context context;
   
   private final int resourceId;
   
   private final MolokoCalendar setDueDateCalendar = MolokoCalendar.getInstance();
   
   
   
   public MinDetailedTasksListFragmentAdapter( Context context, int resourceId )
   {
      this( context, resourceId, Collections.< Task > emptyList() );
   }
   
   
   
   public MinDetailedTasksListFragmentAdapter( Context context, int resourceId,
      List< Task > tasks )
   {
      super( context, View.NO_ID, tasks );
      
      this.context = context;
      this.resourceId = resourceId;
   }
   
   
   
   public int getLayoutRessource()
   {
      return resourceId;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
         convertView = ( (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) ).inflate( resourceId,
                                                                                                                 parent,
                                                                                                                 false );
      final View priority = convertView.findViewById( R.id.taskslist_listitem_priority );
      
      ImageView completed;
      TextView description;
      TextView dueDate;
      
      try
      {
         completed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_check );
         description = (TextView) convertView.findViewById( R.id.taskslist_listitem_desc );
         dueDate = (TextView) convertView.findViewById( R.id.taskslist_listitem_due_date );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final Task task = getItem( position );
      
      UIUtils.setTaskDescription( description, task, MolokoDateUtils.newTime() );
      
      setDueDate( dueDate, task );
      
      UIUtils.setPriorityColor( priority, task );
      
      setCompleted( completed, task );
      
      return convertView;
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
               
               // Is it next week?
               else
               {
                  MolokoCalendar calNextWeek = nowCal;
                  calNextWeek.roll( Calendar.WEEK_OF_YEAR, true );
                  nowCal = null;
                  
                  if ( calNextWeek.get( Calendar.WEEK_OF_YEAR ) == cal.get( Calendar.WEEK_OF_YEAR ) )
                  {
                     // we only show the week day
                     dueText = MolokoDateUtils.getDayOfWeekString( cal.get( Calendar.DAY_OF_WEEK ),
                                                                   false );
                  }
                  
                  // Not the same week and not next week
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
