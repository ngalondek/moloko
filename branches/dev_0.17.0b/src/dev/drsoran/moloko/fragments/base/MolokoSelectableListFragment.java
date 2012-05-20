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

package dev.drsoran.moloko.fragments.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;

import com.actionbarsherlock.view.ActionMode;

import dev.drsoran.moloko.IOnSelectionChangesListener;
import dev.drsoran.moloko.actionmodes.BaseSelectableActionModeCallback;
import dev.drsoran.moloko.actionmodes.listener.IBaseSelectableActionModeListener;
import dev.drsoran.moloko.adapters.ISelectableAdapter;
import dev.drsoran.moloko.annotations.InstanceState;


public abstract class MolokoSelectableListFragment< D extends Parcelable >
         extends MolokoListFragment< D > implements OnItemLongClickListener,
         IOnSelectionChangesListener< D >, IBaseSelectableActionModeListener
{
   @InstanceState( key = "is_selection_mode",
                   defaultValue = InstanceState.NO_DEFAULT )
   private boolean isSelectionMode;
   
   @InstanceState( key = "selected_items", defaultValue = InstanceState.NEW )
   private ArrayList< D > selectedItems;
   
   private ActionMode activeActionMode;
   
   private BaseSelectableActionModeCallback< D > actionModeCallback;
   
   
   
   protected MolokoSelectableListFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           MolokoSelectableListFragment.class );
   }
   
   
   
   public void startSelectionMode( Collection< ? extends D > preselectedItems )
   {
      if ( !isSelectionMode )
      {
         activateSelectionMode( preselectedItems );
      }
   }
   
   
   
   public void stopSelectionMode()
   {
      if ( isSelectionMode )
      {
         deactivateSelectionMode();
      }
   }
   
   
   
   public boolean isSelectionMode()
   {
      return isSelectionMode;
   }
   
   
   
   public List< D > getSelectedItems()
   {
      return selectedItems;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      getListView().setOnItemLongClickListener( this );
   }
   
   
   
   @Override
   public boolean onItemLongClick( AdapterView< ? > parent,
                                   View view,
                                   int position,
                                   long id )
   {
      final D item = getSelectableListAdapter().getItem( position );
      final List< D > selectedItems = Collections.singletonList( item );
      
      startSelectionMode( selectedItems );
      
      return true;
   }
   
   
   
   @Override
   public void onSelectionChanged( Collection< ? extends D > items,
                                   boolean isSelected )
   {
      updateSelectedItems();
      
      if ( activeActionMode != null )
      {
         activeActionMode.invalidate();
      }
   }
   
   
   
   @Override
   public void onListAdapterCreated( ListAdapter listAdapter, List< D > result )
   {
      super.onListAdapterCreated( listAdapter, result );
      
      if ( isSelectionMode )
      {
         if ( activeActionMode == null )
         {
            activateSelectionMode( selectedItems );
         }
         else
         {
            configureListAdapterForSelectionMode();
            actionModeCallback.attachAdapter( getSelectableListAdapter() );
            activeActionMode.invalidate();
         }
      }
   }
   
   
   
   @Override
   public void onFinishActionMode()
   {
      deactivateSelectionMode();
   }
   
   
   
   protected void onSelectionModeStarted( ActionMode mode,
                                          BaseSelectableActionModeCallback< D > callback )
   {
   }
   
   
   
   protected void onSelectionModeStopped( ActionMode mode,
                                          BaseSelectableActionModeCallback< D > callback )
   {
      final ISelectableAdapter< D > adapter = getSelectableListAdapter();
      
      adapter.setOnSelectionChangesListener( null );
      adapter.deselectAll();
   }
   
   
   
   protected void configureListAdapterForSelectionMode()
   {
      final ISelectableAdapter< D > adapter = getSelectableListAdapter();
      
      adapter.selectBulk( selectedItems );
      adapter.setOnSelectionChangesListener( this );
   }
   
   
   
   private void activateSelectionMode( Collection< ? extends D > preselectedItems )
   {
      isSelectionMode = true;
      selectedItems = new ArrayList< D >( preselectedItems );
      
      configureListAdapterForSelectionMode();
      
      actionModeCallback = createActionModeCallback();
      actionModeCallback.setActionModeListener( this );
      activeActionMode = getSherlockActivity().startActionMode( actionModeCallback );
      
      onSelectionModeStarted( activeActionMode, actionModeCallback );
   }
   
   
   
   private void deactivateSelectionMode()
   {
      isSelectionMode = false;
      selectedItems.clear();
      
      final ActionMode actionMode = activeActionMode;
      final BaseSelectableActionModeCallback< D > callBack = actionModeCallback;
      
      actionModeCallback = null;
      activeActionMode = null;
      
      onSelectionModeStopped( actionMode, callBack );
   }
   
   
   
   private void updateSelectedItems()
   {
      selectedItems = new ArrayList< D >( getSelectableListAdapter().getSelectedItems() );
   }
   
   
   
   protected abstract BaseSelectableActionModeCallback< D > createActionModeCallback();
   
   
   
   protected abstract ISelectableAdapter< D > getSelectableListAdapter();
}
