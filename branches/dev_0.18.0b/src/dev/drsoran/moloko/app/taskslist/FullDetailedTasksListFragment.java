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

package dev.drsoran.moloko.app.taskslist;

import java.util.List;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;
import dev.drsoran.rtm.Task;


public class FullDetailedTasksListFragment extends
         AbstractTasksListFragment< Task >
{
   public static FullDetailedTasksListFragment newInstance( Bundle configuration )
   {
      final FullDetailedTasksListFragment fragment = new FullDetailedTasksListFragment();
      
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
   public void resortTasks( int newTaskSort )
   {
      getListAdapter().sort( newTaskSort );
   }
   
   
   
   @Override
   public int getDefaultTaskSort()
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
      return loader;
   }
   
   
   
   @Override
   public SwappableArrayAdapter< Task > createListAdapter( IFilter filter )
   {
      final int flags = 0;
      return new FullDetailedTasksListFragmentAdapter( getMolokoListView(),
                                                       filter,
                                                       flags );
   }
   
   
   
   @Override
   public FullDetailedTasksListFragmentAdapter getListAdapter()
   {
      return (FullDetailedTasksListFragmentAdapter) super.getListAdapter();
   }
}
