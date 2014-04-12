/*
 * Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskslist;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.sort.CompositeComparator;
import dev.drsoran.moloko.domain.sort.SortTaskDueDate;
import dev.drsoran.moloko.domain.sort.SortTaskName;
import dev.drsoran.moloko.domain.sort.SortTaskPriority;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.adapters.MultiChoiceModalArrayAdapter;
import dev.drsoran.moloko.ui.format.RtmStyleTaskDateFormatter;
import dev.drsoran.moloko.ui.format.RtmStyleTaskDescTextViewFormatter;
import dev.drsoran.moloko.ui.widgets.SimpleLineView;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterTokenCollection;


class TasksListFragmentAdapter extends MultiChoiceModalArrayAdapter< Task >
{
   public final static int FLAG_SHOW_ALL = 1 << 0;
   
   private final RtmSmartFilterTokenCollection rtmSmartFilterTokens;
   
   private final String[] tagsToRemove;
   
   private final int flags;
   
   private final RtmStyleTaskDateFormatter taskDateFormatter;
   
   private final IRtmCalendarProvider calendarProvider;
   
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
   
   
   
   public TasksListFragmentAdapter( AppContext context, ListView listView,
      RtmSmartFilterTokenCollection rtmSmartFilterTokens, int flags )
   {
      super( listView, R.layout.fulldetailed_taskslist_listitem );
      
      this.flags = flags;
      this.rtmSmartFilterTokens = rtmSmartFilterTokens;
      
      this.tagsToRemove = determineTagsToRemove( rtmSmartFilterTokens, flags );
      
      this.taskDateFormatter = new RtmStyleTaskDateFormatter( getUiContext() );
      
      final AppContext appContext = AppContext.get( getContext() );
      this.lastTasksSort = appContext.getSettings().getTaskSort();
      this.calendarProvider = appContext.getCalendarProvider();
      
      registerDataSetObserver( dataSetObserver );
   }
   
   
   
   private String[] determineTagsToRemove( RtmSmartFilterTokenCollection rtmSmartFilterTokens,
                                           int flags )
   {
      if ( ( flags & FLAG_SHOW_ALL ) != FLAG_SHOW_ALL )
      {
         final List< String > tagsToRemove = new ArrayList< String >();
         
         for ( RtmSmartFilterToken token : rtmSmartFilterTokens )
         {
            if ( token.operatorType == RtmSmartFilterLexer.OP_TAG
               && !token.isNegated )
            {
               tagsToRemove.add( token.value );
            }
         }
         
         final String[] tags = new String[ tagsToRemove.size() ];
         tagsToRemove.toArray( tags );
         
         return tags;
      }
      
      return null;
   }
   
   
   
   public void sort( int taskSort )
   {
      if ( lastTasksSort != taskSort )
      {
         switch ( taskSort )
         {
            case SettingConstants.TASK_SORT_PRIORITY:
               sort( new CompositeComparator< Task >( new SortTaskPriority() ).add( new SortTaskDueDate() ) );
               break;
            
            case SettingConstants.TASK_SORT_DUE_DATE:
               sort( new CompositeComparator< Task >( new SortTaskDueDate() ).add( new SortTaskPriority() ) );
               break;
            
            case SettingConstants.TASK_SORT_NAME:
               sort( new SortTaskName() );
               break;
            
            default :
               throw new IllegalArgumentException( MessageFormat.format( "{0} is no valid sort",
                                                                         taskSort ) );
         }
         
         lastTasksSort = taskSort;
      }
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      convertView = super.getView( position, convertView, parent );
      
      defaultInitializeListItemView( position, convertView );
      
      if ( !isInMultiChoiceModalActionMode() )
      {
         final SimpleLineView priority = (SimpleLineView) convertView.findViewById( R.id.taskslist_listitem_priority );
         final ViewGroup additionalsLayout = (ViewGroup) convertView.findViewById( R.id.taskslist_listitem_additionals_container );
         final TextView listName = (TextView) convertView.findViewById( R.id.taskslist_listitem_btn_list_name );
         final TextView location = (TextView) convertView.findViewById( R.id.taskslist_listitem_location );
         final ImageView recurrent = (ImageView) convertView.findViewById( R.id.taskslist_listitem_recurrent );
         final ImageView hasNotes = (ImageView) convertView.findViewById( R.id.taskslist_listitem_has_notes );
         final ImageView postponed = (ImageView) convertView.findViewById( R.id.taskslist_listitem_postponed );
         
         final Task task = getItem( position );
         
         UiUtils.setPriorityColor( getContext(), priority, task );
         
         recurrent.setVisibility( task.getRecurrence() != null ? View.VISIBLE
                                                              : View.GONE );
         hasNotes.setVisibility( task.hasNotes() ? View.VISIBLE : View.GONE );
         postponed.setVisibility( task.isPostponed() ? View.VISIBLE : View.GONE );
         
         setListName( listName, task );
         
         setTags( additionalsLayout, task );
         
         setLocation( location, task );
      }
      
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
         return getItem( position ).getId();
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
                                                            calendarProvider.getNowMillisUtc() );
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
      view.setEnabled( task.isComplete() );
   }
   
   
   
   private void setListName( TextView view, Task task )
   {
      if ( ( flags & FLAG_SHOW_ALL ) == FLAG_SHOW_ALL
         || !rtmSmartFilterTokens.hasUniqueOperatorWithValue( RtmSmartFilterLexer.OP_LIST,
                                                              task.getListName(),
                                                              false ) )
      {
         view.setVisibility( View.VISIBLE );
         view.setText( task.getListName() );
      }
      else
      {
         view.setVisibility( View.GONE );
      }
   }
   
   
   
   private void setLocation( TextView view, Task task )
   {
      // If the task has no location
      if ( ( flags & FLAG_SHOW_ALL ) != FLAG_SHOW_ALL
         && ( TextUtils.isEmpty( task.getLocationName() ) || rtmSmartFilterTokens.hasUniqueOperatorWithValue( RtmSmartFilterLexer.OP_LOCATION,
                                                                                                              task.getLocationName(),
                                                                                                              false ) ) )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         view.setText( task.getLocationName() );
      }
   }
   
   
   
   private void setTags( ViewGroup tagsLayout, Task task )
   {
      final Bundle tagsConfig = new Bundle();
      
      if ( tagsToRemove != null && tagsToRemove.length > 0 )
      {
         tagsConfig.putStringArray( UiUtils.REMOVE_TAGS_EQUALS, tagsToRemove );
      }
      
      UiUtils.inflateTags( getContext(), tagsLayout, task.getTags(), tagsConfig );
   }
}
