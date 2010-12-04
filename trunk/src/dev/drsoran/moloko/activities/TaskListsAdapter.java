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

package dev.drsoran.moloko.activities;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class TaskListsAdapter extends BaseExpandableListAdapter
{
   private final static String TAG = "Moloko."
      + TaskListsAdapter.class.getName();
   
   private final Context context;
   
   public final static int DUE_TODAY_TASK_COUNT = 1;
   
   public final static int DUE_TOMORROW_TASK_COUNT = 2;
   
   public final static int OVER_DUE_TASK_COUNT = 3;
   
   public final static int COMPLETED_TASK_COUNT = 4;
   
   public final static int SUM_ESTIMATE = 5;
   
   private final static int ID_ICON_DEFAULT_LIST = 1;
   
   private final static int ID_ICON_LOCKED = 2;
   
   private final int groupId;
   
   private final int childId;
   
   private final LayoutInflater inflater;
   
   private final ArrayList< RtmListWithTaskCount > lists;
   
   

   public TaskListsAdapter( Context context, int groupId, int childId,
      ArrayList< RtmListWithTaskCount > lists )
   {
      super();
      
      if ( lists == null )
         throw new NullPointerException( "lists must not be null" );
      
      this.context = context;
      this.groupId = groupId;
      this.childId = childId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      this.lists = lists;
   }
   


   public Object getChild( int groupPosition, int childPosition )
   {
      switch ( childPosition + 1 )
      {
         case DUE_TODAY_TASK_COUNT:
            return new Integer( lists.get( groupPosition )
                                     .getExtendedListInfo( context ).dueTodayTaskCount );
         case DUE_TOMORROW_TASK_COUNT:
            return new Integer( lists.get( groupPosition )
                                     .getExtendedListInfo( context ).dueTomorrowTaskCount );
         case OVER_DUE_TASK_COUNT:
            return new Integer( lists.get( groupPosition )
                                     .getExtendedListInfo( context ).overDueTaskCount );
         case COMPLETED_TASK_COUNT:
            return new Integer( lists.get( groupPosition )
                                     .getExtendedListInfo( context ).completedTaskCount );
         case SUM_ESTIMATE:
            return MolokoDateUtils.formatEstimated( context,
                                                    lists.get( groupPosition )
                                                         .getExtendedListInfo( context ).sumEstimated );
         default :
            return null;
      }
   }
   


   public Intent getChildIntent( int groupPosition, int childPosition )
   {
      final RtmListWithTaskCount list = lists.get( groupPosition );
      final String title = list.getName() + " -";
      
      Intent intent = null;
      
      switch ( childPosition + 1 )
      {
         case DUE_TODAY_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_LIT
                                                      + DateParser.tokenNames[ DateParser.TODAY ] );
            intent.removeExtra( AbstractTasksListActivity.TITLE );
            intent.putExtra( AbstractTasksListActivity.TITLE,
                             context.getString( R.string.tasklists_child_due_today,
                                                title ) );
            break;
         
         case DUE_TOMORROW_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_LIT
                                                      + DateParser.tokenNames[ DateParser.TOMORROW ] );
            intent.removeExtra( AbstractTasksListActivity.TITLE );
            intent.putExtra( AbstractTasksListActivity.TITLE,
                             context.getString( R.string.tasklists_child_due_tomorrow,
                                                title ) );
            break;
         
         case OVER_DUE_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                      + DateParser.tokenNames[ DateParser.TODAY ] );
            intent.removeExtra( AbstractTasksListActivity.TITLE );
            intent.putExtra( AbstractTasksListActivity.TITLE,
                             context.getString( R.string.tasklists_child_overdue,
                                                title ) );
            break;
         
         case COMPLETED_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_STATUS_LIT
                                                      + RtmSmartFilterLexer.COMPLETED_LIT );
            intent.removeExtra( AbstractTasksListActivity.TITLE );
            intent.putExtra( AbstractTasksListActivity.TITLE,
                             context.getString( R.string.tasklists_child_completed,
                                                title ) );
            break;
         
         default :
            break;
      }
      
      return intent;
   }
   


   public long getChildId( int groupPosition, int childPosition )
   {
      return childPosition + 1;
   }
   


   public View getChildView( int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent )
   {
      View view;
      
      if ( convertView != null && convertView.getId() == R.id.tasklists_child )
      {
         view = convertView;
      }
      else
      {
         view = inflater.inflate( childId, null, false );
      }
      
      if ( view != null )
      {
         final TextView textView = (TextView) view;
         
         switch ( childPosition + 1 )
         {
            case DUE_TODAY_TASK_COUNT:
               textView.setText( context.getString( R.string.tasklists_child_due_today,
                                                    Integer.toString( (Integer) getChild( groupPosition,
                                                                                          childPosition ) ) ) );
               break;
            
            case DUE_TOMORROW_TASK_COUNT:
               textView.setText( context.getString( R.string.tasklists_child_due_tomorrow,
                                                    Integer.toString( (Integer) getChild( groupPosition,
                                                                                          childPosition ) ) ) );
               break;
            
            case OVER_DUE_TASK_COUNT:
               textView.setText( context.getString( R.string.tasklists_child_overdue,
                                                    Integer.toString( (Integer) getChild( groupPosition,
                                                                                          childPosition ) ) ) );
               break;
            
            case COMPLETED_TASK_COUNT:
               textView.setText( context.getString( R.string.tasklists_child_completed,
                                                    Integer.toString( (Integer) getChild( groupPosition,
                                                                                          childPosition ) ) ) );
               break;
            
            case SUM_ESTIMATE:
               textView.setText( context.getString( R.string.task_datetime_estimate_inline,
                                                    getChild( groupPosition,
                                                              childPosition ) ) );
               break;
            
            default :
               break;
         }
         
      }
      
      return view;
   }
   


   public int getChildrenCount( int groupPosition )
   {
      return SUM_ESTIMATE;
   }
   


   public Object getGroup( int groupPosition )
   {
      return lists.get( groupPosition );
   }
   


   public int getGroupCount()
   {
      return lists.size();
   }
   


   public long getGroupId( int groupPosition )
   {
      return Long.valueOf( lists.get( groupPosition ).getId() );
   }
   


   public View getGroupView( int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent )
   {
      View view;
      
      if ( convertView != null && convertView.getId() == R.id.tasklists_group )
      {
         view = convertView;
      }
      else
      {
         view = inflater.inflate( groupId, null, false );
      }
      
      if ( view != null )
      {
         ImageView groupIndicator;
         TextView listName;
         TextView tasksCount;
         ViewGroup iconsContainer;
         
         try
         {
            groupIndicator = (ImageView) view.findViewById( R.id.tasklists_group_indicator );
            listName = (TextView) view.findViewById( R.id.tasklists_group_list_name );
            tasksCount = (TextView) view.findViewById( R.id.tasklists_group_num_tasks );
            iconsContainer = (ViewGroup) view.findViewById( R.id.tasklists_group_icons_container );
         }
         catch ( ClassCastException e )
         {
            Log.e( TAG, "Invalid layout spec.", e );
            throw e;
         }
         
         if ( isExpanded )
         {
            groupIndicator.setImageResource( R.drawable.expander_ic_maximized );
         }
         else
         {
            groupIndicator.setImageResource( R.drawable.expander_ic_minimized );
         }
         
         final RtmListWithTaskCount rtmList = lists.get( groupPosition );
         
         final String listNameStr = rtmList.getName();
         final int numTasks = rtmList.getIncompleteTaskCount();
         
         listName.setText( listNameStr );
         
         tasksCount.setText( String.valueOf( numTasks ) );
         
         // If we have a smart filter we check if it could
         // be evaluated. If so add the filter to show in list
         // as name. Otherwise mark it explicitly with null
         // as bad filter
         if ( rtmList.hasSmartFilter() )
         {
            if ( rtmList.isSmartFilterValid() )
            {
               tasksCount.setBackgroundResource( R.drawable.tasklists_group_numtasks_bgnd_smart );
            }
            else
            {
               tasksCount.setBackgroundResource( R.drawable.tasklists_group_numtasks_bgnd_smart_fail );
               tasksCount.setText( "?" );
            }
         }
         else
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_group_numtasks_bgnd );
         }
         
         addConditionalIcon( iconsContainer,
                             R.drawable.ic_list_tasklists_flag,
                             ID_ICON_DEFAULT_LIST,
                             rtmList.getId()
                                    .equals( MolokoApp.getSettings()
                                                      .getDefaultListId() ) );
         addConditionalIcon( iconsContainer,
                             R.drawable.ic_list_tasklists_lock,
                             ID_ICON_LOCKED,
                             rtmList.getLocked() != 0 );
      }
      
      return view;
   }
   


   public boolean hasStableIds()
   {
      return true;
   }
   


   public boolean isChildSelectable( int groupPosition, int childPosition )
   {
      return childPosition != SUM_ESTIMATE;
   }
   


   private void addConditionalIcon( ViewGroup container,
                                    int resId,
                                    int iconId,
                                    boolean condition )
   {
      ImageView icon = (ImageView) container.findViewById( iconId );
      
      // if not already added
      if ( condition && icon == null )
      {
         container.setVisibility( View.VISIBLE );
         
         icon = (ImageView) ImageView.inflate( context,
                                               R.layout.tasklists_activity_group_icon,
                                               null );
         
         if ( icon != null )
         {
            icon.setId( iconId );
            icon.setImageResource( resId );
            container.addView( icon );
         }
      }
      else if ( !condition && icon != null )
      {
         container.removeView( icon );
         
         if ( container.getChildCount() == 0 )
            container.setVisibility( View.GONE );
      }
   }
}
