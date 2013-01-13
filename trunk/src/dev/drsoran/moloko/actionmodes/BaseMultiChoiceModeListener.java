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

import java.util.Collection;

import android.content.Context;
import android.database.DataSetObserver;
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
   private class ListDatasetObserver extends DataSetObserver
   {
      @Override
      public void onChanged()
      {
         super.onChanged();
         if ( actionMode != null )
         {
            actionMode.invalidate();
         }
      }
      
      
      
      @Override
      public void onInvalidated()
      {
         super.onInvalidated();
         if ( actionMode != null )
         {
            actionMode.invalidate();
         }
      }
   }
   
   private final DataSetObserver dataSetObserver;
   
   private final MolokoListView listView;
   
   private IBaseSelectableActionModeListener< T > listener;
   
   private ActionMode actionMode;
   
   
   
   public BaseMultiChoiceModeListener( MolokoListView listView )
   {
      this.listView = listView;
      this.dataSetObserver = new ListDatasetObserver();
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
      final ListAdapter adapter = getAdapter();
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
      return listView.getCheckedItems();
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      actionMode = mode;
      
      mode.getMenuInflater().inflate( R.menu.selection_mode_female, menu );
      getAdapter().registerDataSetObserver( dataSetObserver );
      
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
      }
      
      getAdapter().unregisterDataSetObserver( dataSetObserver );
      actionMode = null;
   }
   
   
   
   @Override
   public void onItemCheckedStateChanged( ActionMode mode,
                                          int position,
                                          long id,
                                          boolean checked )
   {
      updateTitle( mode );
      mode.invalidate();
   }
   
   
   
   /**
    * Even if the list returns a checked count > 0, the data in the adapter may not yet exist and is still in loading.
    * The checked state is stored separate in the ListView.
    */
   public boolean hasLoaderData()
   {
      return getAdapter().getCount() > 0;
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
