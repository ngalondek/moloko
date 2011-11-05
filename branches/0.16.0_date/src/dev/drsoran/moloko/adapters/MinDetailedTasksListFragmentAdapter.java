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

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class MinDetailedTasksListFragmentAdapter extends ArrayAdapter< Task >
{
   private final static String TAG = "Moloko."
      + MinDetailedTasksListFragmentAdapter.class.getName();
   
   private final Context context;
   
   private final int resourceId;
   
   

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
   


   private final void setDueDate( TextView view, Task task )
   {
      // if has a due date
      if ( task.getDue() != null )
      {
         view.setVisibility( View.VISIBLE );
         
         String dueText = null;
         
         final long dueMillis = task.getDue().getTime();
         final boolean hasDueTime = task.hasDueTime();
         
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
         else
         {
            final Time now = MolokoDateUtils.newTime();
            final Time dueTime = MolokoDateUtils.newTime( dueMillis );
            
            // If it is the same year
            if ( dueTime.year == now.year )
            {
               // If the same week and in the future
               if ( now.getWeekNumber() == dueTime.getWeekNumber()
                  && dueTime.after( now ) )
               {
                  // we only show the week day
                  dueText = MolokoDateUtils.getDayOfWeekString( dueTime.weekDay + 1,
                                                                false );
               }
               
               // Not the same week or same week but in the past
               else
               {
                  // we show the date but w/o year
                  dueText = MolokoDateUtils.formatDate( context,
                                                        dueMillis,
                                                        MolokoDateUtils.FORMAT_ABR_MONTH );
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
