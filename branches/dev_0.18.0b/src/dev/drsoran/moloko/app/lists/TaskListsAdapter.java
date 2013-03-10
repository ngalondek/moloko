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

package dev.drsoran.moloko.app.lists;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.rtm.RtmListWithTaskCount;


class TaskListsAdapter extends BaseExpandableListAdapter
{
   public interface IOnGroupIndicatorClickedListener
   {
      void onGroupIndicatorClicked( View groupView );
   }
   
   /** State indicating the group is expanded. */
   private static final int[] GROUP_EXPANDED_STATE_SET =
   { android.R.attr.state_expanded };
   
   public final static int DUE_TODAY_TASK_COUNT = 1;
   
   public final static int DUE_TOMORROW_TASK_COUNT = 2;
   
   public final static int OVER_DUE_TASK_COUNT = 3;
   
   public final static int COMPLETED_TASK_COUNT = 4;
   
   public final static int SUM_ESTIMATE = 5;
   
   private final static int ID_ICON_DEFAULT_LIST = 1;
   
   private final static int ID_ICON_LOCKED = 2;
   
   private final AppContext context;
   
   private final View.OnClickListener iconExpandCollapseListener = new View.OnClickListener()
   {
      @Override
      public void onClick( View view )
      {
         if ( groupIndicatorClickedListener != null )
            groupIndicatorClickedListener.onGroupIndicatorClicked( view );
      }
   };
   
   private final int groupId;
   
   private final int childId;
   
   private final LayoutInflater inflater;
   
   private final ArrayList< RtmListWithTaskCount > lists;
   
   private StateListDrawable groupIndicatorDrawable;
   
   private IOnGroupIndicatorClickedListener groupIndicatorClickedListener;
   
   
   
   public TaskListsAdapter( AppContext context, int groupId, int childId,
      List< RtmListWithTaskCount > lists )
   {
      this.context = context;
      this.groupId = groupId;
      this.childId = childId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      this.lists = new ArrayList< RtmListWithTaskCount >( lists );
      
      obtainGroupIndicatorDrawable();
   }
   
   
   
   public void setOnGroupIndicatorClickedListener( IOnGroupIndicatorClickedListener listener )
   {
      groupIndicatorClickedListener = listener;
   }
   
   
   
   @Override
   public Object getChild( int groupPosition, int childPosition )
   {
      switch ( childPosition + 1 )
      {
         case DUE_TODAY_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).dueTodayTaskCount );
         case DUE_TOMORROW_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).dueTomorrowTaskCount );
         case OVER_DUE_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).overDueTaskCount );
         case COMPLETED_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).completedTaskCount );
         case SUM_ESTIMATE:
            return context.getDateFormatter()
                          .formatEstimated( lists.get( groupPosition )
                                                 .getExtendedListInfo( context ).sumEstimated );
         default :
            return null;
      }
   }
   
   
   
   public Intent getChildIntent( int groupPosition, int childPosition )
   {
      final RtmListWithTaskCount list = lists.get( groupPosition );
      Intent intent = null;
      
      switch ( childPosition + 1 )
      {
         case DUE_TODAY_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_LIT
                                                      + DateParser.tokenNames[ DateParser.TODAY ] );
            intent.putExtra( Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                             context.getString( R.string.taskslist_actionbar_subtitle_due_today ) );
            break;
         
         case DUE_TOMORROW_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_LIT
                                                      + DateParser.tokenNames[ DateParser.TOMORROW ] );
            intent.putExtra( Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                             context.getString( R.string.taskslist_actionbar_subtitle_due_tomorrow ) );
            break;
         
         case OVER_DUE_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                      + DateParser.tokenNames[ DateParser.TODAY ] );
            intent.putExtra( Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                             context.getString( R.string.taskslist_actionbar_subtitle_overdue ) );
            break;
         
         case COMPLETED_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_STATUS_LIT
                                                      + RtmSmartFilterLexer.COMPLETED_LIT );
            intent.putExtra( Intents.Extras.KEY_ACTIVITY_SUB_TITLE,
                             context.getString( R.string.taskslist_actionbar_subtitle_completed ) );
            break;
         
         default :
            break;
      }
      
      return intent;
   }
   
   
   
   @Override
   public long getChildId( int groupPosition, int childPosition )
   {
      return childPosition + 1;
   }
   
   
   
   @Override
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
         final Object childObject = getChild( groupPosition, childPosition );
         final int quantity = childObject instanceof Integer
                                                            ? ( (Integer) getChild( groupPosition,
                                                                                    childPosition ) ).intValue()
                                                            : -1;
         
         switch ( childPosition + 1 )
         {
            case DUE_TODAY_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_due_today,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case DUE_TOMORROW_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_due_tomorrow,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case OVER_DUE_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_overdue,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case COMPLETED_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_completed,
                                                            quantity,
                                                            quantity ) );
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
   
   
   
   @Override
   public int getChildrenCount( int groupPosition )
   {
      return SUM_ESTIMATE;
   }
   
   
   
   @Override
   public Object getGroup( int groupPosition )
   {
      return lists.get( groupPosition );
   }
   
   
   
   @Override
   public int getGroupCount()
   {
      return lists.size();
   }
   
   
   
   @Override
   public long getGroupId( int groupPosition )
   {
      return Long.valueOf( lists.get( groupPosition ).getId() );
   }
   
   
   
   @Override
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
      
      final ImageView groupIndicator = (ImageView) view.findViewById( R.id.tasklists_group_indicator );
      final TextView listName = (TextView) view.findViewById( R.id.tasklists_group_list_name );
      final TextView tasksCount = (TextView) view.findViewById( R.id.tasklists_group_num_tasks );
      final ViewGroup iconsContainer = (ViewGroup) view.findViewById( R.id.tasklists_group_icons_container );
      
      groupIndicatorDrawable.setState( isExpanded ? GROUP_EXPANDED_STATE_SET
                                                 : new int[] {} );
      
      groupIndicator.setOnClickListener( iconExpandCollapseListener );
      
      final Drawable drawable = groupIndicatorDrawable.getCurrent();
      groupIndicator.setImageDrawable( drawable );
      
      final RtmListWithTaskCount rtmList = lists.get( groupPosition );
      final String listNameStr = rtmList.getName();
      
      listName.setText( listNameStr );
      
      UiUtils.setListTasksCountView( tasksCount, rtmList );
      
      addConditionalIcon( iconsContainer,
                          R.drawable.ic_list_tasklists_flag,
                          ID_ICON_DEFAULT_LIST,
                          rtmList.getId().equals( context.getSettings()
                                                         .getDefaultListId() ) );
      addConditionalIcon( iconsContainer,
                          R.drawable.ic_list_tasklists_lock,
                          ID_ICON_LOCKED,
                          rtmList.getLocked() != 0 );
      
      return view;
   }
   
   
   
   @Override
   public boolean hasStableIds()
   {
      return true;
   }
   
   
   
   @Override
   public boolean isChildSelectable( int groupPosition, int childPosition )
   {
      return childPosition != SUM_ESTIMATE;
   }
   
   
   
   private void obtainGroupIndicatorDrawable()
   {
      TypedArray a = null;
      try
      {
         final Theme theme = context.getTheme();
         final TypedValue outValue = new TypedValue();
         context.getTheme()
                .resolveAttribute( android.R.attr.expandableListViewStyle,
                                   outValue,
                                   true );
         a = theme.obtainStyledAttributes( outValue.resourceId, new int[]
         { android.R.attr.groupIndicator } );
         
         groupIndicatorDrawable = (StateListDrawable) a.getDrawable( 0 );
      }
      finally
      {
         if ( a != null )
         {
            a.recycle();
            a = null;
         }
      }
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
                                               R.layout.tasklists_fragment_group_icon,
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
