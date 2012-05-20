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

package dev.drsoran.moloko.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.actionbarsherlock.view.ActionMode;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.BaseSelectableActionModeCallback;
import dev.drsoran.moloko.actionmodes.TasksListActionModeCallback;
import dev.drsoran.moloko.adapters.ISelectableAdapter;
import dev.drsoran.moloko.adapters.MinDetailedTasksListFragmentAdapter;
import dev.drsoran.moloko.loaders.TasksLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.Task;


public class MinDetailedTasksListFragment extends
         AbstractTasksListFragment< Task > implements
         IOnSettingsChangedListener
{
   public static MinDetailedTasksListFragment newInstance( Bundle configuration )
   {
      final MinDetailedTasksListFragment fragment = new MinDetailedTasksListFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   
   
   
   @Override
   protected int getDefaultTaskSort()
   {
      return MolokoApp.getSettings( getSherlockActivity() ).getTaskSort();
   }
   
   
   
   @Override
   public Loader< List< Task >> newLoaderInstance( int id, Bundle config )
   {
      final IFilter filter = config.getParcelable( Intents.Extras.KEY_FILTER );
      final String selection = filter != null ? filter.getSqlSelection() : null;
      final String order = resolveTaskSortToSqlite( config.getInt( Intents.Extras.KEY_TASK_SORT_ORDER ) );
      
      final TasksLoader loader = new TasksLoader( getSherlockActivity(),
                                                  selection,
                                                  order );
      loader.setUpdateThrottle( DEFAULT_LOADER_THROTTLE_MS );
      
      return loader;
   }
   
   
   
   @Override
   public ListAdapter createListAdapterForResult( List< Task > result,
                                                  IFilter filter )
   {
      return new MinDetailedTasksListFragmentAdapter( getSherlockActivity(),
                                                      result );
   }
   
   
   
   @Override
   public MinDetailedTasksListFragmentAdapter getListAdapter()
   {
      return (MinDetailedTasksListFragmentAdapter) super.getListAdapter();
   }
   
   
   
   @Override
   protected BaseSelectableActionModeCallback< Task > createActionModeCallback()
   {
      return new TasksListActionModeCallback( getSherlockActivity(),
                                              getListAdapter() );
   }
   
   
   
   @Override
   protected void configureListAdapterForSelectionMode()
   {
      super.configureListAdapterForSelectionMode();
      getListAdapter().setSelectable( true );
   }
   
   
   
   @Override
   protected void onSelectionModeStopped( ActionMode mode,
                                          BaseSelectableActionModeCallback< Task > callback )
   {
      super.onSelectionModeStopped( mode, callback );
      getListAdapter().setSelectable( false );
   }
   
   
   
   @Override
   protected ISelectableAdapter< Task > getSelectableListAdapter()
   {
      return getListAdapter();
   }
}
