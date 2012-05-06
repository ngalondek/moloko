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

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.MinDetailedTasksListFragmentAdapter;
import dev.drsoran.moloko.fragments.listeners.IMinDetailedTasksListFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListFragmentListener;
import dev.drsoran.moloko.loaders.TasksLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoMenuItemBuilder;
import dev.drsoran.rtm.Task;


public class MinDetailedTasksListFragment extends
         AbstractTasksListFragment< Task > implements
         IOnSettingsChangedListener
{
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intents.Action.TASKS_LISTS_MIN_DETAILED,
                                           "vnd.android.cursor.dir/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   protected static class OptionsMenu extends
            AbstractTasksListFragment.OptionsMenu
   {
      public final static int EDIT_MULTIPLE_TASKS = R.id.menu_edit_multiple_tasks;
   }
   
   private IMinDetailedTasksListFragmentListener listener;
   
   
   
   public static MinDetailedTasksListFragment newInstance( Bundle configuration )
   {
      final MinDetailedTasksListFragment fragment = new MinDetailedTasksListFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IMinDetailedTasksListFragmentListener )
         listener = (IMinDetailedTasksListFragmentListener) activity;
      else
         listener = new NullTasksListFragmentListener();
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      super.onCreateOptionsMenu( menu, inflater );
      
      new MolokoMenuItemBuilder().setItemId( OptionsMenu.EDIT_MULTIPLE_TASKS )
                                 .setTitle( getString( R.string.abstaskslist_menu_opt_edit_multiple ) )
                                 .setIconId( R.drawable.ic_menu_edit_multiple_tasks )
                                 .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_IF_ROOM )
                                 .setShow( hasMultipleTasks()
                                    && isWritableAccess() )
                                 .build( menu );
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.EDIT_MULTIPLE_TASKS:
            listener.onSelectTasks();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
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
                                                      R.layout.mindetailed_taskslist_listitem,
                                                      result );
   }
   
   
   
   @Override
   public MinDetailedTasksListFragmentAdapter getListAdapter()
   {
      return (MinDetailedTasksListFragmentAdapter) super.getListAdapter();
   }
}
