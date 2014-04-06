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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Loader;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.loaders.TasksLoader;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.app.taskslist.common.IShowTasksWithTagsListener.LogicalOperation;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.actionmodes.BaseMultiChoiceModeListener;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;
import dev.drsoran.moloko.ui.fragments.MolokoListFragment;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterTokenCollection;


public class TasksListFragment extends MolokoListFragment< Task > implements
         ITasksListActionModeListener, IQuickAddTaskActionModeListener,
         IOnSettingsChangedListener
{
   private AppContext appContext;
   
   @InstanceState( key = "ACTIONMODE_QUICK_ADD_TASK" )
   private boolean quickAddTaskActionModeActive;
   
   @InstanceState( key = Intents.Extras.KEY_LIST_ID,
                   defaultValue = InstanceState.NO_ID )
   private long listId;
   
   @InstanceState( key = Intents.Extras.KEY_FILTER )
   private RtmSmartFilter filter;
   
   @InstanceState( key = Intents.Extras.KEY_TASK_SORT_ORDER, defaultValue = "1" /* TASK_SORT_PRIORITY */)
   private int tasksSort;
   
   private ActionMode activeActionMode;;
   
   
   
   public TasksListFragment()
   {
      registerAnnotatedConfiguredInstance( this, TasksListFragment.class );
      setNoElementsResourceId( R.string.abstaskslist_no_tasks );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      appContext = AppContext.get( activity );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
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
      
      if ( quickAddTaskActionModeActive )
      {
         showQuickAddTaskInput();
      }
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
         case R.id.menu_quick_add_task:
            showQuickAddTaskInput();
            return true;
            
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
   
   
   
   private void resortTasks( int newTaskSort )
   {
      getListAdapter().sort( newTaskSort );
   }
   
   
   
   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      getActivity().getMenuInflater()
                   .inflate( R.menu.taskslist_listitem_context, menu );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      final Task selectedTask = getTask( info.position );
      
      prepareSingleTaskActionMenu( menu, selectedTask );
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
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      final Task selectedTask = getTask( info.position );
      
      switch ( item.getItemId() )
      {
         case R.id.menu_open_tags:
            onShowTasksWithTags( selectedTask.getTags() );
            handled = true;
            break;
         
         case R.id.menu_open_tasks_at_loc:
            onOpenLocation( selectedTask.getLocationName() );
            handled = true;
            break;
         
         default :
            break;
      }
      
      if ( !handled )
      {
         return super.onContextItemSelected( item );
      }
      
      return true;
   }
   
   
   
   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      onOpenTask( position );
   }
   
   
   
   private void showQuickAddTaskInput()
   {
      activeActionMode = getActivity().startActionMode( new QuickAddTaskActionModeCallback( this,
                                                                                            getFilter() ) );
      quickAddTaskActionModeActive = true;
   }
   
   
   
   private void finishActiveActionMode()
   {
      if ( activeActionMode != null )
      {
         activeActionMode.finish();
         quickAddTaskActionModeActive = false;
      }
   }
   
   
   
   private void onOpenTask( int pos )
   {
      startActivity( Intents.createOpenTaskIntent( getActivity(),
                                                   getTask( pos ).getId() ) );
   }
   
   
   
   @Override
   public final void onShowTasksWithTags( Collection< String > tags )
   {
      if ( tags.size() == 1 )
      {
         onOpenChoosenTags( tags, null );
      }
      else if ( tags.size() > 1 )
      {
         showChooseTagsDialog( tags );
      }
   }
   
   
   
   private void onOpenChoosenTags( Collection< String > tags,
                                   String logicalOperation )
   {
      startActivity( Intents.createOpenTasksWithTagsIntent( getActivity(),
                                                            new ArrayList< String >( tags ),
                                                            logicalOperation ) );
   }
   
   
   
   private void showChooseTagsDialog( Collection< String > tags )
   {
      ChooseTagsDialogFragment.show( getActivity(), tags );
   }
   
   
   
   /*
    * Callback from ChooseTagsDialogFragment after choosing tags and a logical operation on them.
    */
   public final void onShowTasksWithTags( Collection< String > tags,
                                          LogicalOperation operation )
   {
      final String logOpString = determineLogicalOperationString( operation );
      onOpenChoosenTags( tags, logOpString );
   }
   
   
   
   private static String determineLogicalOperationString( LogicalOperation operation )
   {
      final String logOpString;
      
      switch ( operation )
      {
         case AND:
            logOpString = RtmSmartFilterSyntax.AND;
            break;
         
         case OR:
            logOpString = RtmSmartFilterSyntax.OR;
            break;
         
         default :
            logOpString = null;
            break;
      }
      return logOpString;
   }
   
   
   
   @Override
   public void onQuickAddAddNewTask( Bundle parsedValues )
   {
      finishActiveActionMode();
      startActivity( Intents.createAddTaskIntent( getActivity(), parsedValues ) );
   }
   
   
   
   @Override
   public void onOpenLocation( String locationName )
   {
      startActivity( Intents.createShowTasksWithLocationNameIntent( getActivity(),
                                                                    locationName ) );
   }
   
   
   
   @Override
   public void onEditTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         startActivity( Intents.createEditTaskIntent( getActivity(),
                                                      tasks.get( 0 ) ) );
      }
      else
      {
         startActivity( Intents.createEditMultipleTasksIntent( getActivity(),
                                                               tasks ) );
      }
      
      finishActiveActionMode();
   }
   
   
   
   @Override
   public void onCompleteTasks( List< ? extends Task > tasks )
   {
      completeSelectedTasks( tasks );
   }
   
   
   
   @Override
   public void onIncompleteTasks( List< ? extends Task > tasks )
   {
      incompleteSelectedTasks( tasks );
   }
   
   
   
   @Override
   public void onPostponeTasks( List< ? extends Task > tasks )
   {
      postponeSelectedTasks( tasks );
   }
   
   
   
   @Override
   public void onDeleteTasks( List< ? extends Task > tasks )
   {
      deleteSelectedTasks( tasks );
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
   
   
   
   private RtmSmartFilter getFilter()
   {
      return filter;
   }
   
   
   
   private boolean hasMultipleTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 1;
   }
   
   
   
   private Task getTask( int pos )
   {
      return getListAdapter().getItem( pos );
   }
   
   
   
   private int getTaskCount()
   {
      return getListAdapter() != null ? getListAdapter().getCount() : 0;
   }
   
   
   
   private int getTaskSort()
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
   public Loader< List< Task >> newLoaderInstance( int id, Bundle config )
   {
      final TasksLoader loader;
      
      final RtmSmartFilter filter = (RtmSmartFilter) config.getSerializable( Intents.Extras.KEY_FILTER );
      if ( filter != null )
      {
         loader = new TasksLoader( getAppContext().asDomainContext(),
                                   filter,
                                   TaskContentOptions.WithNotes );
      }
      else
      {
         loader = new TasksLoader( getAppContext().asDomainContext(),
                                   config.getLong( Intents.Extras.KEY_LIST_ID ),
                                   TaskContentOptions.WithNotes );
      }
      
      loader.setRespectContentChanges( true );
      
      return loader;
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
         getListAdapter().notifyDataSetChanged();
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
      final int flags = 0;
      return new TasksListFragmentAdapter( getAppContext(),
                                           getListView(),
                                           getFilterTokens( getFilter() ),
                                           flags );
   }
   
   
   
   @Override
   public TasksListFragmentAdapter getListAdapter()
   {
      return (TasksListFragmentAdapter) super.getListAdapter();
   }
   
   
   
   private RtmSmartFilterTokenCollection getFilterTokens( RtmSmartFilter filter )
   {
      if ( filter != null )
      {
         try
         {
            return getAppContext().getParsingService()
                                  .getSmartFilterParsing()
                                  .getSmartFilterTokens( filter.getFilterString() );
         }
         catch ( GrammarException e )
         {
            Log().e( getClass(), "Unparsable smart filter", e );
         }
      }
      
      return new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
   }
   
   
   
   @Override
   public int getChoiceMode()
   {
      return hasWritableAccess() ? ListView.CHOICE_MODE_MULTIPLE_MODAL
                                : ListView.CHOICE_MODE_NONE;
   }
   
   
   
   public BaseMultiChoiceModeListener< Task > createMultiCoiceModalModeListener()
   {
      final TasksListActionModeCallback callback = new TasksListActionModeCallback( this );
      callback.setTasksListActionModeListener( this );
      
      return callback;
   }
   
   
   
   public FragmentTransaction addButtonBarFragment( Fragment fragmentToAdd )
   {
      final Fragment bottomFragment = getButtonBarFragment();
      final FragmentTransaction transaction = getFragmentManager().beginTransaction()
                                                                  .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
      
      if ( bottomFragment == null )
      {
         transaction.add( R.id.frag_multi_container, fragmentToAdd );
      }
      else
      {
         transaction.replace( R.id.frag_multi_container, fragmentToAdd );
      }
      
      return transaction;
   }
   
   
   
   public QuickAddTaskButtonBarFragment getButtonBarFragment()
   {
      final QuickAddTaskButtonBarFragment addedFragment = (QuickAddTaskButtonBarFragment) getFragmentManager().findFragmentById( R.id.frag_quick_add_task_button_bar );
      if ( addedFragment.isAdded() && !addedFragment.isRemoving() )
      {
         return addedFragment;
      }
      
      return null;
   }
   
   
   
   public Fragment removeButtonBarFragment()
   {
      final Fragment bottomFragment = getButtonBarFragment();
      
      if ( bottomFragment != null )
      {
         getFragmentManager().beginTransaction()
                             .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_CLOSE )
                             .remove( bottomFragment )
                             .commit();
      }
      
      return bottomFragment;
   }
   
   
   
   private void completeSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().completeTasks( getActivity(),
                                                             tasks );
      clearListChoices();
   }
   
   
   
   private void incompleteSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().incompleteTasks( getActivity(),
                                                               tasks );
      clearListChoices();
   }
   
   
   
   private void postponeSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().postponeTasks( getActivity(),
                                                             tasks );
      clearListChoices();
   }
   
   
   
   private void deleteSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService()
                     .deleteTasks( getActivity(), tasks );
      clearListChoices();
   }
   
   
   
   private void clearListChoices()
   {
      getListView().clearChoices();
   }
}
