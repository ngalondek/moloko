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

package dev.drsoran.moloko.app.taskslist.common;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.internal.widget.CapitalizingTextView;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.taskslist.common.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;
import dev.drsoran.rtm.RtmListWithTaskCount;


class TasksListNavigationAdapter extends SwappableArrayAdapter< IItem >
{
   public static interface IItem
   {
      long getId();
      
      
      
      String getPrimaryText();
      
      
      
      String getSupplementalText();
      
      
      
      int getNumberOfTasks();
      
      
      
      Bundle getTasksListConfig();
   }
   
   
   public static class RtmListItem implements IItem
   {
      private final Context context;
      
      private final RtmListWithTaskCount list;
      
      
      
      public RtmListItem( Context context, RtmListWithTaskCount list )
      {
         this.context = context;
         this.list = list;
      }
      
      
      
      @Override
      public long getId()
      {
         return Long.valueOf( list.getId() );
      }
      
      
      
      @Override
      public String getPrimaryText()
      {
         return list.getName();
      }
      
      
      
      @Override
      public String getSupplementalText()
      {
         return null;
      }
      
      
      
      @Override
      public int getNumberOfTasks()
      {
         return list.getNumTasksParticipating();
      }
      
      
      
      @Override
      public Bundle getTasksListConfig()
      {
         return Intents.Extras.createOpenListExtras( context, list, null );
      }
   }
   
   
   public static class ExtendedRtmListItem extends RtmListItem
   {
      private final String supplementalText;
      
      private final int numTasks;
      
      private Bundle config;
      
      
      
      public ExtendedRtmListItem( Context context, String typeOfTasks,
         RtmListWithTaskCount list, int numTasks )
      {
         super( context, list );
         this.supplementalText = typeOfTasks;
         this.numTasks = numTasks;
      }
      
      
      
      @Override
      public String getSupplementalText()
      {
         return supplementalText;
      }
      
      
      
      @Override
      public int getNumberOfTasks()
      {
         return numTasks;
      }
      
      
      
      @Override
      public Bundle getTasksListConfig()
      {
         return config;
      }
      
      
      
      public ExtendedRtmListItem setTasksListConfig( Bundle config )
      {
         this.config = config;
         return this;
      }
   }
   
   
   public final static class CustomItem implements IItem
   {
      private final long id;
      
      private final String primaryText;
      
      private final String supplemental;
      
      private final Bundle config;
      
      
      
      public CustomItem( long id, String primaryText, String supplemental,
         Bundle config )
      {
         this.id = id;
         this.primaryText = primaryText;
         this.supplemental = supplemental;
         this.config = config;
      }
      
      
      
      @Override
      public long getId()
      {
         return id;
      }
      
      
      
      @Override
      public String getPrimaryText()
      {
         return primaryText;
      }
      
      
      
      @Override
      public String getSupplementalText()
      {
         return supplemental;
      }
      
      
      
      @Override
      public int getNumberOfTasks()
      {
         return -1;
      }
      
      
      
      @Override
      public Bundle getTasksListConfig()
      {
         return config;
      }
   }
   
   
   private abstract class AbstractDropDownPresenter
   {
      public View getSpinnerItemView( int position,
                                      View convertView,
                                      ViewGroup parent )
      {
         final int resId = getSpinnerLayoutResourceId( position );
         convertView = inflateIfNeeded( convertView, parent, resId );
         
         initializeSelectedItemView( position, convertView, parent );
         
         return convertView;
      }
      
      
      
      public View getDropDownItemView( int position,
                                       View convertView,
                                       ViewGroup parent )
      {
         final int listSectionIndex = getListSectionIndex();
         
         if ( position < listSectionIndex )
         {
            convertView = inflateIfNeeded( convertView,
                                           parent,
                                           getDropdownLayoutResourceId( position ) );
            
            initializeSelectedItemView( position, convertView, parent );
         }
         else if ( position == listSectionIndex )
         {
            convertView = inflateIfNeeded( convertView,
                                           parent,
                                           R.layout.taskslist_activity_actionbar_listsection_dropdown_item );
            
            final CapitalizingTextView textView = (CapitalizingTextView) convertView.findViewById( android.R.id.text1 );
            textView.setTextCompat( getContext().getString( R.string.app_tasklists ) );
         }
         // Normal list
         else
         {
            convertView = inflateIfNeeded( convertView,
                                           parent,
                                           R.layout.sherlock_spinner_dropdown_item );
            
            // Subtract 1 from the position since the list section item is not part of the adapter
            // data.
            final String title = getItem( position - 1 ).getPrimaryText();
            final TextView itemTextView = (TextView) convertView.findViewById( android.R.id.text1 );
            itemTextView.setText( title );
         }
         
         return convertView;
      }
      
      
      
      public int getItemViewType( int position )
      {
         final int listSectionIndex = getListSectionIndex();
         if ( position < listSectionIndex )
         {
            return getDropdownLayoutResourceId( position );
         }
         else if ( position == listSectionIndex )
         {
            return R.layout.taskslist_activity_actionbar_listsection_dropdown_item;
         }
         // Normal list
         else
         {
            return R.layout.sherlock_spinner_dropdown_item;
         }
      }
      
      
      
      private void initializeSelectedItemView( int position,
                                               View convertView,
                                               ViewGroup parent )
      {
         final IItem item = getItem( position );
         
         final TextView text1View = (TextView) convertView.findViewById( android.R.id.text1 );
         if ( text1View != null )
         {
            setText( text1View, item.getPrimaryText() );
         }
         
         final TextView text2View = (TextView) convertView.findViewById( android.R.id.text2 );
         if ( text2View != null )
         {
            setText( text2View, item.getSupplementalText() );
         }
         
         final TextView numTasksView = (TextView) convertView.findViewById( R.id.numTasks );
         if ( numTasksView != null )
         {
            numTasksView.setText( String.valueOf( item.getNumberOfTasks() ) );
         }
      }
      
      
      
      private void setText( TextView textView, String text )
      {
         if ( textView instanceof CapitalizingTextView )
         {
            ( (CapitalizingTextView) textView ).setTextCompat( text );
         }
         else
         {
            textView.setText( text );
         }
      }
      
      
      
      public abstract int getSpinnerLayoutResourceId( int position );
      
      
      
      public abstract int getDropdownLayoutResourceId( int position );
      
      
      
      public abstract int getListSectionIndex();
   }
   
   
   private class CustomItemDropDownPresenter extends AbstractDropDownPresenter
   {
      @Override
      public int getSpinnerLayoutResourceId( int position )
      {
         return R.layout.sherlock_spinner_dropdown_item;
      }
      
      
      
      @Override
      public int getDropdownLayoutResourceId( int position )
      {
         return R.layout.sherlock_spinner_dropdown_item;
      }
      
      
      
      @Override
      public int getListSectionIndex()
      {
         return 1;
      }
   }
   
   
   private class RtmListItemDropDownPresenter extends AbstractDropDownPresenter
   {
      @Override
      public int getSpinnerLayoutResourceId( int position )
      {
         if ( position == ITEM_POSITION_DEFAULT_TASKS )
         {
            return R.layout.sherlock_spinner_dropdown_item;
         }
         else
         {
            return R.layout.taskslist_activity_actionbar_spinner_item_2line;
         }
      }
      
      
      
      @Override
      public int getDropdownLayoutResourceId( int position )
      {
         if ( position == ITEM_POSITION_DEFAULT_TASKS )
         {
            return R.layout.taskslist_activity_actionbar_rtmlist_dropdown_item;
         }
         else
         {
            return R.layout.taskslist_activity_actionbar_extendedrtmlist_dropdown_item;
         }
      }
      
      
      
      @Override
      public int getListSectionIndex()
      {
         return 5;
      }
   }
   
   public final static int ITEM_POSITION_DEFAULT_TASKS = 0;
   
   public final static int ITEM_POSITION_COMPLETED_TASKS = 1;
   
   public final static int ITEM_POSITION_OVERDUE_TASKS = 2;
   
   public final static int ITEM_POSITION_TODAY_TASKS = 3;
   
   public final static int ITEM_POSITION_TOMORROW_TASKS = 4;
   
   private final LayoutInflater inflater;
   
   private AbstractDropDownPresenter dropDownPresenter;
   
   
   
   public TasksListNavigationAdapter( Context context, List< IItem > items )
   {
      super( context, 0, items );
      
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      
      if ( items.get( 0 ) instanceof CustomItem )
      {
         dropDownPresenter = new CustomItemDropDownPresenter();
      }
      else
      {
         dropDownPresenter = new RtmListItemDropDownPresenter();
      }
   }
   
   
   
   public Bundle getTasksListConfigForItem( int position )
   {
      return getItem( getListSectionAwareItemPosition( position ) ).getTasksListConfig();
   }
   
   
   
   public IItem getItem( long id )
   {
      IItem item = null;
      
      for ( int i = 0, cnt = getCount(); i < cnt && item == null; i++ )
      {
         final IItem tempItem = getItem( i );
         if ( tempItem.getId() == id )
         {
            item = tempItem;
         }
      }
      
      return item;
   }
   
   
   
   public IItem getItemByDisplay( String display )
   {
      IItem item = null;
      
      for ( int i = 0, cnt = getCount(); i < cnt && item == null; i++ )
      {
         final IItem tempItem = getItem( i );
         if ( tempItem.getPrimaryText().equals( display ) )
         {
            item = tempItem;
         }
      }
      
      return item;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return getItem( getListSectionAwareItemPosition( position ) ).getId();
   }
   
   
   
   @Override
   public int getItemViewType( int position )
   {
      return dropDownPresenter.getItemViewType( position );
   }
   
   
   
   @Override
   public boolean areAllItemsEnabled()
   {
      return false;
   }
   
   
   
   @Override
   public boolean isEnabled( int position )
   {
      return dropDownPresenter.getListSectionIndex() != position;
   }
   
   
   
   @Override
   public View getDropDownView( int position, View convertView, ViewGroup parent )
   {
      return dropDownPresenter.getDropDownItemView( position,
                                                    convertView,
                                                    parent );
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      return dropDownPresenter.getSpinnerItemView( position,
                                                   convertView,
                                                   parent );
   }
   
   
   
   private int getListSectionAwareItemPosition( int position )
   {
      return position >= dropDownPresenter.getListSectionIndex() ? position - 1
                                                                : position;
   }
   
   
   
   private View inflateIfNeeded( View convertView, ViewGroup parent, int resId )
   {
      if ( convertView == null
         || ( (Integer) convertView.getTag() ).intValue() != resId )
      {
         convertView = inflater.inflate( resId, parent, false );
         convertView.setTag( Integer.valueOf( resId ) );
      }
      
      return convertView;
   }
}
