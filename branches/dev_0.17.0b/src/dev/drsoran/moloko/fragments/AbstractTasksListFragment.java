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
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoListFragment;
import dev.drsoran.moloko.fragments.listeners.ITasksListFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListFragmentListener;
import dev.drsoran.moloko.loaders.TasksLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public abstract class AbstractTasksListFragment< T extends Task > extends
         MolokoListFragment< List< T > >
{
   protected static class OptionsMenu
   {
      public final static int SORT = R.id.menu_sort;
      
      public final static int SORT_PRIO = R.id.menu_sort_priority;
      
      public final static int SORT_DUE = R.id.menu_sort_due;
      
      public final static int SORT_NAME = R.id.menu_sort_task_name;
      
      public final static int SETTINGS = R.id.menu_settings;
   }
   
   
   protected static class OptionsMenuGroup
   {
      public final static int SORT = R.id.menu_group_sort;
   }
   
   protected final static long DEFAULT_LOADER_THROTTLE_MS = 1 * DateUtils.SECOND_IN_MILLIS;
   
   private ITasksListFragmentListener listener;
   
   @InstanceState( key = Intents.Extras.KEY_FILTER )
   private IFilter filter;
   
   @InstanceState( key = Intents.Extras.KEY_TASK_SORT_ORDER,
                   settingsValue = "getTaskSort" )
   private int tasksSort;
   
   
   
   public AbstractTasksListFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AbstractTasksListFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      ensureFilter();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITasksListFragmentListener )
         listener = (ITasksListFragmentListener) activity;
      else
         listener = new NullTasksListFragmentListener();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      setHasOptionsMenu( true );
   }
   
   
   
   protected CharSequence getEmptyListText()
   {
      return getString( R.string.abstaskslist_no_tasks );
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      final SubMenu subMenu = createTasksSortSubMenu( menu );
      if ( subMenu != null )
      {
         subMenu.setGroupCheckable( OptionsMenuGroup.SORT, true, true );
         
         final int currentTaskSort = getTaskSort();
         initializeTasksSortSubMenu( subMenu, currentTaskSort );
      }
   }
   
   
   
   @Override
   public final void onPrepareOptionsMenu( Menu menu )
   {
      final MenuItem sortMenuItem = menu.findItem( OptionsMenu.SORT );
      
      if ( sortMenuItem != null )
      {
         final int currentTaskSort = getTaskSort();
         initializeTasksSortSubMenu( sortMenuItem.getSubMenu(), currentTaskSort );
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.SORT_PRIO:
            resortTasks( Settings.TASK_SORT_PRIORITY );
            item.setChecked( true );
            return true;
            
         case OptionsMenu.SORT_DUE:
            resortTasks( Settings.TASK_SORT_DUE_DATE );
            item.setChecked( true );
            return true;
            
         case OptionsMenu.SORT_NAME:
            resortTasks( Settings.TASK_SORT_NAME );
            item.setChecked( true );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   protected SubMenu createTasksSortSubMenu( Menu menu )
   {
      SubMenu subMenu = null;
      
      if ( hasMultipleTasks() )
      {
         subMenu = menu.addSubMenu( OptionsMenuGroup.SORT,
                                    OptionsMenu.SORT,
                                    MenuCategory.SECONDARY,
                                    R.string.abstaskslist_menu_opt_sort );
         
         subMenu.setIcon( R.drawable.ic_menu_sort );
         subMenu.getItem().setShowAsAction( MenuItem.SHOW_AS_ACTION_IF_ROOM );
         
         subMenu.add( OptionsMenuGroup.SORT,
                      OptionsMenu.SORT_PRIO,
                      Menu.NONE,
                      R.string.app_sort_prio );
         
         subMenu.add( OptionsMenuGroup.SORT,
                      OptionsMenu.SORT_DUE,
                      Menu.NONE,
                      R.string.app_sort_due_date );
         
         subMenu.add( OptionsMenuGroup.SORT,
                      OptionsMenu.SORT_NAME,
                      Menu.NONE,
                      R.string.app_sort_alpha );
      }
      
      return subMenu;
   }
   
   
   
   protected void initializeTasksSortSubMenu( SubMenu subMenu,
                                              int currentTaskSort )
   {
      // INFO: These items are exclusive checkable. Setting one will reset the other.
      // The setChecked() call parameter gets ignored. Only the call matters and
      // always sets the item.
      switch ( currentTaskSort )
      {
         case Settings.TASK_SORT_PRIORITY:
            subMenu.findItem( OptionsMenu.SORT_PRIO ).setChecked( true );
            break;
         
         case Settings.TASK_SORT_DUE_DATE:
            subMenu.findItem( OptionsMenu.SORT_DUE ).setChecked( true );
            break;
         
         case Settings.TASK_SORT_NAME:
            subMenu.findItem( OptionsMenu.SORT_NAME ).setChecked( true );
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      listener.onOpenTask( position );
   }
   
   
   
   protected void resortTasks( int newTaskSort )
   {
      if ( shouldResortTasks( newTaskSort ) )
         listener.onTaskSortChanged( newTaskSort );
   }
   
   
   
   public IFilter getFilter()
   {
      return filter;
   }
   
   
   
   public RtmSmartFilter getRtmSmartFilter()
   {
      final IFilter filter = getFilter();
      return ( filter instanceof RtmSmartFilter ) ? (RtmSmartFilter) filter
                                                 : null;
   }
   
   
   
   private void ensureFilter()
   {
      if ( filter == null )
         filter = new RtmSmartFilter( Strings.EMPTY_STRING );
   }
   
   
   
   public boolean hasTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 0;
   }
   
   
   
   public boolean hasMultipleTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 1;
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   public T getTask( int pos )
   {
      return (T) getListAdapter().getItem( pos );
   }
   
   
   
   public int getTaskPos( View view )
   {
      return getListView().getPositionForView( view );
   }
   
   
   
   public T getTask( View view )
   {
      return getTask( getTaskPos( view ) );
   }
   
   
   
   public T getTask( String taskId )
   {
      T task = null;
      
      final ListAdapter adapter = getListAdapter();
      final int itemCount = adapter.getCount();
      for ( int i = 0; i < itemCount && task == null; i++ )
      {
         @SuppressWarnings(
         { "unchecked" } )
         final T temp = (T) adapter.getItem( i );
         if ( temp.getId().equals( taskId ) )
         {
            task = temp;
         }
      }
      
      return task;
   }
   
   
   
   public void reload()
   {
      getLoaderManager().restartLoader( getLoaderId(), getConfiguration(), this );
   }
   
   
   
   public int getTaskSort()
   {
      return tasksSort;
   }
   
   
   
   public boolean shouldResortTasks( int taskSort )
   {
      return getTaskSort() != taskSort;
   }
   
   
   
   protected String resolveTaskSortToSqlite( int taskSort )
   {
      return Queries.resolveTaskSortToSqlite( taskSort );
   }
   
   
   
   @Override
   public void showError( CharSequence errorMessage )
   {
      showError( errorMessage );
      getLoaderManager().destroyLoader( getLoaderId() );
   }
   
   
   
   @Override
   public void showError( Spanned errorMessage )
   {
      showError( errorMessage );
      getLoaderManager().destroyLoader( getLoaderId() );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< T >> loader, List< T > data )
   {
      super.onLoadFinished( loader, data );
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TasksLoader.ID;
   }
   
   
   
   @Override
   protected int getSettingsMask()
   {
      return super.getSettingsMask()
         | IOnSettingsChangedListener.DATE_TIME_RELATED;
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_task );
   }
   
   
   
   @Override
   public ListAdapter createListAdapterForResult( List< T > result )
   {
      return createListAdapterForResult( result, getFilter() );
   }
   
   
   
   protected abstract int getDefaultTaskSort();
   
   
   
   protected abstract ListAdapter createListAdapterForResult( List< T > result,
                                                              IFilter filter );
}
