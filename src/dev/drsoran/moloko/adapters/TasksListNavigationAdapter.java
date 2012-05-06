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

package dev.drsoran.moloko.adapters;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.TasksListNavigationAdapter.IItem;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class TasksListNavigationAdapter extends ArrayAdapter< IItem >
{
   public static interface IItem
   {
      String getId();
      
      
      
      String getDisplay();
      
      
      
      Bundle getTasksListConfig();
   }
   
   
   public final static class RtmListItem implements IItem
   {
      private final Context context;
      
      private final RtmListWithTaskCount list;
      
      
      
      public RtmListItem( Context context, RtmListWithTaskCount list )
      {
         this.context = context;
         this.list = list;
      }
      
      
      
      @Override
      public String getId()
      {
         return list.getId();
      }
      
      
      
      @Override
      public String getDisplay()
      {
         return list.getName();
      }
      
      
      
      @Override
      public Bundle getTasksListConfig()
      {
         return Intents.Extras.createOpenListExtras( context, list, null );
      }
   }
   
   
   public final static class CustomItem implements IItem
   {
      private final String id;
      
      private final String display;
      
      private final Bundle config;
      
      
      
      public CustomItem( String id, String display, Bundle config )
      {
         this.id = id;
         this.display = display;
         this.config = config;
      }
      
      
      
      @Override
      public String getId()
      {
         return id;
      }
      
      
      
      @Override
      public String getDisplay()
      {
         return display;
      }
      
      
      
      @Override
      public Bundle getTasksListConfig()
      {
         return config;
      }
   }
   
   private final LayoutInflater inflater;
   
   private int selectedItemIndex;
   
   
   
   public TasksListNavigationAdapter( Context context, List< IItem > items )
   {
      super( context, 0, items );
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
   }
   
   
   
   public void setSelectedItemIndex( int selectedItemIndex )
   {
      if ( this.selectedItemIndex != selectedItemIndex )
      {
         this.selectedItemIndex = selectedItemIndex;
         notifyDataSetChanged();
      }
   }
   
   
   
   public Bundle getTasksListConfigForItem( int position )
   {
      return getItem( position ).getTasksListConfig();
   }
   
   
   
   public IItem getItem( String id )
   {
      IItem item = null;
      
      for ( int i = 0, cnt = getCount(); i < cnt && item == null; i++ )
      {
         final IItem tempItem = getItem( i );
         if ( tempItem.getId().equals( id ) )
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
         if ( tempItem.getDisplay().equals( display ) )
         {
            item = tempItem;
         }
      }
      
      return item;
   }
   
   
   
   @Override
   public View getDropDownView( int position, View convertView, ViewGroup parent )
   {
      final View view = createViewFromResource( position,
                                                convertView,
                                                parent,
                                                R.layout.sherlock_spinner_dropdown_item );
      setSelectedItemStyle( position, view );
      return view;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = createViewFromResource( position,
                                                convertView,
                                                parent,
                                                R.layout.sherlock_spinner_item );
      return view;
   }
   
   
   
   private View createViewFromResource( int position,
                                        View convertView,
                                        ViewGroup parent,
                                        int layoutResourceId )
   {
      if ( convertView == null )
         convertView = inflater.inflate( layoutResourceId, parent, false );
      
      final String item = getItem( position ).getDisplay();
      final TextView itemTextView = (TextView) convertView.findViewById( android.R.id.text1 );
      itemTextView.setText( item );
      
      return convertView;
   }
   
   
   
   private void setSelectedItemStyle( int position, View spinnerItem )
   {
      final TextView itemTextView = (TextView) spinnerItem.findViewById( android.R.id.text1 );
      spinnerItem.setSelected( position == selectedItemIndex );
      
      if ( spinnerItem.isSelected() )
      {
         itemTextView.setTextAppearance( getContext(),
                                         R.style.TasksListSpinnerItem_Active );
      }
      else
      {
         itemTextView.setTextAppearance( getContext(),
                                         R.style.TasksListSpinnerItem );
      }
   }
}
