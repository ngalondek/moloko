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

package dev.drsoran.moloko.app.taskslist.common;

import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.sort.CompositeComparator;
import dev.drsoran.moloko.sort.SortTaskDueDate;
import dev.drsoran.moloko.sort.SortTaskName;
import dev.drsoran.moloko.sort.SortTaskPriority;
import dev.drsoran.moloko.ui.adapters.LayoutSwitchMultiChoiceModalAdapter;
import dev.drsoran.moloko.ui.format.RtmStyleTaskDateFormatter;
import dev.drsoran.moloko.ui.format.RtmStyleTaskDescTextViewFormatter;
import dev.drsoran.moloko.ui.widgets.MolokoListView;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.Task;


abstract class AbstractTasksListFragmentAdapter extends
         LayoutSwitchMultiChoiceModalAdapter< Task >
{
   private final RtmStyleTaskDateFormatter taskDateFormatter;
   
   private final DataSetObserver dataSetObserver = new DataSetObserver()
   {
      @Override
      public void onChanged()
      {
         super.onChanged();
         lastTasksSort = -1;
      }
      
      
      
      @Override
      public void onInvalidated()
      {
         super.onInvalidated();
         lastTasksSort = -1;
      }
   };
   
   private int lastTasksSort;
   
   
   
   protected AbstractTasksListFragmentAdapter( MolokoListView listView,
      int unselectedResourceId, int selectedResourceId )
   {
      super( listView, unselectedResourceId, selectedResourceId );
      
      taskDateFormatter = new RtmStyleTaskDateFormatter( getUiContext() );
      lastTasksSort = AppContext.get( getContext() )
                                .getSettings()
                                .getTaskSort();
      
      registerDataSetObserver( dataSetObserver );
   }
   
   
   
   public void sort( int taskSort )
   {
      if ( lastTasksSort != taskSort )
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
               throw new IllegalArgumentException( taskSort
                  + " is no valid sort" );
         }
         
         lastTasksSort = taskSort;
      }
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      convertView = super.getView( position, convertView, parent );
      
      defaultInitializeListItemView( position, convertView );
      
      return convertView;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      // Note: We handle the case for queries out of bounds because the
      // Android runtime asks us for invalid positions if the last element
      // is removed and we get a changed data set.
      if ( position < getCount() )
      {
         return Long.parseLong( getItem( position ).getId() );
      }
      else
      {
         return -1;
      }
   }
   
   
   
   private void defaultInitializeListItemView( int position, View convertView )
   {
      final ImageView completed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_check );
      final TextView description = (TextView) convertView.findViewById( R.id.taskslist_listitem_desc );
      final TextView dueDate = (TextView) convertView.findViewById( R.id.taskslist_listitem_due_date );
      
      final Task task = getItem( position );
      
      RtmStyleTaskDescTextViewFormatter.setTaskDescription( description,
                                                            task,
                                                            MolokoDateUtils.newTime() );
      setDueDate( dueDate, task );
      setCompleted( completed, task );
   }
   
   
   
   private void setDueDate( TextView view, Task task )
   {
      final String formattedDueDate = taskDateFormatter.getFormattedDueDate( task );
      
      if ( !TextUtils.isEmpty( formattedDueDate ) )
      {
         view.setVisibility( View.VISIBLE );
         view.setText( formattedDueDate );
      }
      else
      {
         view.setVisibility( View.GONE );
      }
   }
   
   
   
   private void setCompleted( ImageView view, Task task )
   {
      view.setEnabled( task.getCompleted() != null );
   }
}
