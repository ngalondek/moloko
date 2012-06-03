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

package dev.drsoran.moloko.actionmodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.widget.ListAdapter;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.listener.IBaseSelectableActionModeListener;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.widgets.MolokoListView;
import dev.drsoran.moloko.widgets.MolokoListView.IMolokoMultiChoiceModeListener;


public class BaseMultiChoiceModeListener< T > implements
         IMolokoMultiChoiceModeListener
{
   private final MolokoListView listView;
   
   private IBaseSelectableActionModeListener< T > listener;
   
   
   
   public BaseMultiChoiceModeListener( MolokoListView listView )
   {
      this.listView = listView;
   }
   
   
   
   public MolokoListView getListView()
   {
      return listView;
   }
   
   
   
   public ListAdapter getAdapter()
   {
      return listView.getAdapter();
   }
   
   
   
   public Context getContext()
   {
      return listView.getContext();
   }
   
   
   
   public void setActionModeListener( IBaseSelectableActionModeListener< T > listener )
   {
      this.listener = listener;
   }
   
   
   
   public void selectAll()
   {
      final ListAdapter adapter = listView.getAdapter();
      final SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
      
      for ( int i = 0, cnt = adapter.getCount(); i < cnt; ++i )
      {
         final boolean isChecked = checkedPositions.get( i );
         if ( !isChecked )
         {
            listView.setItemChecked( i, true );
         }
      }
   }
   
   
   
   public void deselectAll()
   {
      final SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
      
      for ( int i = 0, cnt = checkedPositions.size(); i < cnt; ++i )
      {
         final int position = checkedPositions.keyAt( i );
         final boolean isChecked = checkedPositions.get( position );
         
         if ( isChecked )
         {
            listView.setItemChecked( position, false );
         }
      }
   }
   
   
   
   public void invertSelection()
   {
      final ListAdapter adapter = listView.getAdapter();
      final SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
      
      for ( int i = 0, cnt = adapter.getCount(); i < cnt; ++i )
      {
         final boolean isChecked = checkedPositions.get( i );
         listView.setItemChecked( i, !isChecked );
      }
   }
   
   
   
   public Collection< T > getSelectedItems()
   {
      final Collection< T > selectedItems;
      final ListAdapter adapter = listView.getAdapter();
      
      if ( adapter.getCount() > 0 )
      {
         final SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
         final int numSelected = checkedPositions.size();
         
         selectedItems = new ArrayList< T >( numSelected );
         
         for ( int i = 0; i < numSelected; ++i )
         {
            final int position = checkedPositions.keyAt( i );
            final boolean isChecked = checkedPositions.get( position );
            
            if ( isChecked )
            {
               @SuppressWarnings( "unchecked" )
               final T selectedItem = (T) adapter.getItem( position );
               
               selectedItems.add( selectedItem );
            }
         }
      }
      else
      {
         selectedItems = Collections.emptyList();
      }
      
      return selectedItems;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      mode.getMenuInflater().inflate( R.menu.selection_mode_female, menu );
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      updateTitle( mode );
      return true;
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_select_all:
            selectAll();
            return true;
            
         case R.id.menu_deselect_all:
            deselectAll();
            return true;
            
         case R.id.menu_invert_selection:
            invertSelection();
            return true;
            
         default :
            return false;
      }
   }
   
   
   
   @Override
   public void onDestroyActionMode( ActionMode mode )
   {
      if ( listener != null )
      {
         listener.onFinishingSelectionMode( mode, this );
         listener = null;
      }
   }
   
   
   
   @Override
   public void onItemCheckedStateChanged( ActionMode mode,
                                          int position,
                                          long id,
                                          boolean checked )
   {
      updateTitle( mode );
   }
   
   
   
   private void updateTitle( ActionMode mode )
   {
      final int selectedCnt = listView.getCheckedItemCountSupport();
      final CharSequence title;
      
      if ( selectedCnt > 0 )
      {
         title = getContext().getResources()
                             .getString( R.string.app_selected_count,
                                         selectedCnt );
      }
      else
      {
         title = Strings.EMPTY_STRING;
      }
      
      mode.setTitle( title );
   }
}
