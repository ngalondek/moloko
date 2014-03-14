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

import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.loaders.TasksLoader;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.actionmodes.BaseMultiChoiceModeListener;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;
import dev.drsoran.moloko.ui.fragments.MolokoMultiChoiceModalListFragment;
import dev.drsoran.moloko.ui.widgets.MolokoListView;


abstract class AbstractTasksListFragment extends
         MolokoMultiChoiceModalListFragment< Task > implements
         ITasksListFragment, IOnSettingsChangedListener
{
   private AppContext appContext;
   
   private ITasksListFragmentListener fragmentListener;
   
   private ITasksListActionModeListener actionModeListener;
   
   @InstanceState( key = Intents.Extras.KEY_LIST_ID,
                   defaultValue = InstanceState.NO_ID )
   private long listId;
   
   @InstanceState( key = Intents.Extras.KEY_FILTER )
   private RtmSmartFilter filter;
   
   @InstanceState( key = Intents.Extras.KEY_TASK_SORT_ORDER, defaultValue = "1" /* TASK_SORT_PRIORITY */)
   private int tasksSort;
   
   
   
   public AbstractTasksListFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AbstractTasksListFragment.class );
      setNoElementsResourceId( R.string.abstaskslist_no_tasks );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      appContext = AppContext.get( activity );
      
      if ( activity instanceof ITasksListFragmentListener )
         fragmentListener = (ITasksListFragmentListener) activity;
      else
         fragmentListener = null;
      
      if ( activity instanceof ITasksListActionModeListener )
         actionModeListener = (ITasksListActionModeListener) activity;
      else
         actionModeListener = null;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      if ( !hasWritableAccess() )
      {
         registerForContextMenu( getListView() );
      }
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      appContext.getAppEvents()
                .registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                    this );
   }
   
   
   
   @Override
   public void onStop()
   {
      appContext.getAppEvents().unregisterOnSettingsChangedListener( this );
      super.onStop();
   }
   
   
   
   @Override
   public void onDetach()
   {
      fragmentListener = null;
      actionModeListener = null;
      appContext = null;
      
      super.onDetach();
   }
   
   
   
   public AppContext getAppContext()
   {
      return appContext;
   }
   
   
   
   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      setHasOptionsMenu( true );
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      inflater.inflate( R.menu.tasks_sort, menu );
      super.onCreateOptionsMenu( menu, inflater );
   }
   
   
   
   @Override
   public final void onPrepareOptionsMenu( Menu menu )
   {
      final MenuItem sortMenuItem = menu.findItem( R.id.menu_sort );
      
      if ( sortMenuItem != null )
      {
         if ( hasMultipleTasks() )
         {
            final int currentTaskSort = getTaskSort();
            initializeTasksSortSubMenu( sortMenuItem.getSubMenu(),
                                        currentTaskSort );
         }
         else
         {
            sortMenuItem.setVisible( false );
         }
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_sort_priority:
            tasksSort = SettingConstants.TASK_SORT_PRIORITY;
            resortTasks( tasksSort );
            item.setChecked( true );
            return true;
            
         case R.id.menu_sort_due:
            tasksSort = SettingConstants.TASK_SORT_DUE_DATE;
            resortTasks( tasksSort );
            item.setChecked( true );
            return true;
            
         case R.id.menu_sort_task_name:
            tasksSort = SettingConstants.TASK_SORT_NAME;
            resortTasks( tasksSort );
            item.setChecked( true );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      getSherlockActivity().getMenuInflater()
                           .inflate( R.menu.taskslist_listitem_context, menu );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      final Task selectedTask = getTask( info.position );
      
      prepareSingleTaskActionMenu( new MenuWrapper( menu ), selectedTask );
   }
   
   
   
   public void prepareSingleTaskActionMenu( Menu menu, Task selectedTask )
   {
      final Collection< String > tags = selectedTask.getTags();
      final int tagsCount = tags.size();
      
      final MenuItem openTagsMenuItem = menu.findItem( R.id.menu_open_tags )
                                            .setVisible( tagsCount > 0 );
      if ( openTagsMenuItem.isVisible() )
      {
         openTagsMenuItem.setTitle( getResources().getQuantityString( R.plurals.taskslist_open_tags,
                                                                      tagsCount,
                                                                      tags.iterator()
                                                                          .next() ) );
      }
      
      final MenuItem tasksAtLocationMenuItem = menu.findItem( R.id.menu_open_tasks_at_loc );
      final String locationName = selectedTask.getLocationName();
      final boolean hasLoction = !TextUtils.isEmpty( locationName );
      
      tasksAtLocationMenuItem.setVisible( hasLoction );
      if ( hasLoction )
      {
         tasksAtLocationMenuItem.setTitle( getString( R.string.abstaskslist_listitem_ctx_tasks_at_location,
                                                      locationName ) );
      }
   }
   
   
   
   @Override
   public boolean onContextItemSelected( android.view.MenuItem item )
   {
      boolean handled = false;
      
      if ( actionModeListener != null )
      {
         final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
         final Task selectedTask = getTask( info.position );
         
         switch ( item.getItemId() )
         {
            case R.id.menu_open_tags:
               actionModeListener.onShowTasksWithTags( selectedTask.getTags() );
               handled = true;
               break;
            
            case R.id.menu_open_tasks_at_loc:
               actionModeListener.onOpenTaskLocation( selectedTask );
               handled = true;
               break;
            
            default :
               break;
         }
      }
      
      if ( !handled )
      {
         return super.onContextItemSelected( item );
      }
      
      return true;
   }
   
   
   
   protected void initializeTasksSortSubMenu( Menu menu, int currentTaskSort )
   {
      // INFO: These items are exclusive checkable. Setting one will reset the other.
      // The setChecked() call parameter gets ignored. Only the call matters and
      // always sets the item.
      switch ( currentTaskSort )
      {
         case SettingConstants.TASK_SORT_PRIORITY:
            menu.findItem( R.id.menu_sort_priority ).setChecked( true );
            break;
         
         case SettingConstants.TASK_SORT_DUE_DATE:
            menu.findItem( R.id.menu_sort_due ).setChecked( true );
            break;
         
         case SettingConstants.TASK_SORT_NAME:
            menu.findItem( R.id.menu_sort_task_name ).setChecked( true );
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      if ( fragmentListener != null )
      {
         fragmentListener.onOpenTask( position );
      }
   }
   
   
   
   @Override
   public long getListId()
   {
      return listId;
   }
   
   
   
   @Override
   public RtmSmartFilter getFilter()
   {
      return filter;
   }
   
   
   
   private boolean hasMultipleTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 1;
   }
   
   
   
   @Override
   public Task getTask( int pos )
   {
      return (Task) getListAdapter().getItem( pos );
   }
   
   
   
   @Override
   public int getTaskPos( View view )
   {
      return getListView().getPositionForView( view );
   }
   
   
   
   @Override
   public Task getTask( View view )
   {
      return getTask( getTaskPos( view ) );
   }
   
   
   
   @Override
   public Task getTask( long taskId )
   {
      Task task = null;
      
      final ListAdapter adapter = getListAdapter();
      final int itemCount = adapter.getCount();
      for ( int i = 0; i < itemCount && task == null; i++ )
      {
         final Task temp = (Task) adapter.getItem( i );
         if ( temp.getId() == taskId )
         {
            task = temp;
         }
      }
      
      return task;
   }
   
   
   
   @Override
   public int getTaskCount()
   {
      return getListAdapter() != null ? getListAdapter().getCount() : 0;
   }
   
   
   
   @Override
   public int getTaskSort()
   {
      return tasksSort;
   }
   
   
   
   @Override
   public void showError( CharSequence errorMessage )
   {
      super.showError( errorMessage );
      getLoaderManager().destroyLoader( getLoaderId() );
   }
   
   
   
   @Override
   public void showError( Spanned errorMessage )
   {
      super.showError( errorMessage );
      getLoaderManager().destroyLoader( getLoaderId() );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< Task >> loader, List< Task > data )
   {
      super.onLoadFinished( loader, data );
      
      invalidateOptionsMenu();
      
      if ( data != null )
      {
         resortTasks( tasksSort );
      }
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TasksLoader.ID;
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( ( which & IOnSettingsChangedListener.DATE_TIME_RELATED ) != 0
         && isAdded() && !isRemoving() )
      {
         notifiyDatasetChanged();
      }
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_task );
   }
   
   
   
   @Override
   public SwappableArrayAdapter< Task > createListAdapter()
   {
      return createListAdapter( getFilter() );
   }
   
   
   
   @Override
   public int getChoiceMode()
   {
      return hasWritableAccess() ? MolokoListView.CHOICE_MODE_MULTIPLE_MODAL
                                : MolokoListView.CHOICE_MODE_NONE;
   }
   
   
   
   @Override
   public BaseMultiChoiceModeListener< Task > createMultiCoiceModalModeListener()
   {
      final TasksListActionModeCallback callback = new TasksListActionModeCallback( this );
      callback.setTasksListActionModeListener( actionModeListener );
      
      return callback;
   }
   
   
   
   @Override
   public Fragment getFragment()
   {
      return this;
   }
   
   
   
   protected abstract SwappableArrayAdapter< Task > createListAdapter( RtmSmartFilter filter );
   
   
   
   protected abstract void notifiyDatasetChanged();
}
