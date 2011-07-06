/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import android.support.v4.view.Menu;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Checkable;
import android.widget.ListAdapter;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.SelectableTasksListFragmentAdapter;
import dev.drsoran.moloko.adapters.SelectableTasksListFragmentAdapter.ISelectionChangedListener;
import dev.drsoran.moloko.fragments.listeners.ISelectableTasksListListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListListener;
import dev.drsoran.moloko.loaders.SelectableListTasksLoader;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.SelectableListTask;


public class SelectableTasksListsFragment extends
         AbstractTaskListFragment< SelectableListTask > implements
         IOnSettingsChangedListener, ISelectionChangedListener
{
   private final static String TAG = "Moloko."
      + SelectableTasksListsFragment.class.getSimpleName();
   
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intent.ACTION_PICK,
                                           "vnd.android.cursor.dir/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   protected static class OptionsMenu extends
            AbstractTaskListFragment.OptionsMenu
   {
      public final static int SELECT_ALL = R.id.menu_select_all;
      
      public final static int DESELECT_ALL = R.id.menu_deselect_all;
      
      public final static int INVERT_SELECTION = R.id.menu_invert_selection;
      
      public final static int DO_EDIT = R.id.menu_edit_selected_tasks;
      
      public final static int COMPLETE = R.id.menu_complete_selected_tasks;
      
      public final static int UNCOMPLETE = R.id.menu_uncomplete_selected_tasks;
      
      public final static int POSTPONE = R.id.menu_postpone_selected_tasks;
      
      public final static int DELETE = R.id.menu_delete_selected_tasks;
   }
   

   private final static class CtxtMenu
   {
      public final static int TOGGLE_SELECTION = R.id.ctx_menu_toggle_selection;
   }
   

   public static class Config extends MinDetailedTasksListFragment.Config
   {
      private final static String SELECT_STATE = "check_state";
   }
   
   private final static int SORT_SELECTION_IDX = TaskSortPreference.SORT_ORDER_VALUES.length;
   
   public final static int SORT_SELECTION_VALUE = TAG.hashCode();
   
   

   public static SelectableTasksListsFragment newInstance( Bundle configuration )
   {
      final SelectableTasksListsFragment fragment = new SelectableTasksListsFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   private ISelectableTasksListListener listener;
   
   

   public static IntentFilter getIntentFilter()
   {
      return INTENT_FILTER;
   }
   


   @Override
   public Intent newDefaultIntent()
   {
      return new Intent( INTENT_FILTER.getAction( 0 ), Tasks.CONTENT_URI );
   }
   


   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ISelectableTasksListListener )
         listener = (ISelectableTasksListListener) activity;
      else
         listener = new NullTasksListListener();
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   


   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      
      registerForContextMenu( getListView() );
   }
   


   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      
      putCurrentSelectionStateToConfig( configuration );
      outState.putAll( configuration );
   }
   


   @Override
   public void configure( Bundle config )
   {
      super.configure( config );
      
      if ( config != null )
      {
         if ( config.containsKey( Config.SELECT_STATE ) )
            configuration.putStringArrayList( Config.SELECT_STATE,
                                              config.getStringArrayList( Config.SELECT_STATE ) );
      }
   }
   


   @Override
   public Bundle createDefaultConfiguration()
   {
      final Bundle bundle = super.createDefaultConfiguration();
      
      bundle.putStringArrayList( Config.SELECT_STATE,
                                 new ArrayList< String >( 0 ) );
      
      return bundle;
   }
   


   private final void putCurrentSelectionStateToConfig( Bundle config )
   {
      if ( getListAdapter() != null )
         config.putStringArrayList( Config.SELECT_STATE,
                                    getListAdapter().getSelectedTaskIds() );
   }
   


   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      super.onCreateOptionsMenu( menu, inflater );
      
      menu.add( Menu.NONE,
                OptionsMenu.SELECT_ALL,
                Menu.CATEGORY_CONTAINER,
                R.string.select_multiple_tasks_menu_opt_select_all )
          .setIcon( R.drawable.ic_menu_select_all_tasks );
      
      menu.add( Menu.NONE,
                OptionsMenu.DESELECT_ALL,
                Menu.CATEGORY_CONTAINER,
                R.string.select_multiple_tasks_menu_opt_unselect_all )
          .setIcon( R.drawable.ic_menu_select_no_tasks );
      
      menu.add( Menu.NONE,
                OptionsMenu.INVERT_SELECTION,
                Menu.CATEGORY_CONTAINER,
                R.string.select_multiple_tasks_menu_opt_inv_selection )
          .setIcon( R.drawable.ic_menu_select_invert_tasks );
      
      final SelectableTasksListFragmentAdapter adapter = getListAdapter();
      
      if ( adapter != null )
      {
         final boolean allSelected = adapter.areAllSelected();
         final boolean someSelected = adapter.areSomeSelected();
         final List< SelectableListTask > tasks = adapter.getSelectedTasks();
         final int selCnt = tasks.size();
         
         UIUtils.addOptionalMenuItem( getActivity(),
                                      menu,
                                      OptionsMenu.DO_EDIT,
                                      getString( R.string.select_multiple_tasks_menu_opt_do_edit,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_edit,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      someSelected );
         
         int selCompl = 0, selUncompl = 0;
         
         for ( SelectableListTask task : tasks )
         {
            if ( task.getCompleted() != null )
               ++selCompl;
            else
               ++selUncompl;
         }
         
         // The complete task menu is only shown if all selected tasks are uncompleted
         UIUtils.addOptionalMenuItem( getActivity(),
                                      menu,
                                      OptionsMenu.COMPLETE,
                                      getString( R.string.select_multiple_tasks_menu_opt_complete,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_complete,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      someSelected && selUncompl == selCnt );
         
         // The uncomplete task menu is only shown if all selected tasks are completed
         UIUtils.addOptionalMenuItem( getActivity(),
                                      menu,
                                      OptionsMenu.UNCOMPLETE,
                                      getString( R.string.select_multiple_tasks_menu_opt_uncomplete,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_incomplete,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      someSelected && selCompl == selCnt );
         
         UIUtils.addOptionalMenuItem( getActivity(),
                                      menu,
                                      OptionsMenu.POSTPONE,
                                      getString( R.string.select_multiple_tasks_menu_opt_postpone,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_postponed,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      someSelected );
         
         UIUtils.addOptionalMenuItem( getActivity(),
                                      menu,
                                      OptionsMenu.DELETE,
                                      getString( R.string.select_multiple_tasks_menu_opt_delete,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_trash,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      someSelected );
         
         final MenuItem selAllItem = menu.findItem( OptionsMenu.SELECT_ALL );
         selAllItem.setEnabled( !allSelected );
         
         final MenuItem deselAllItem = menu.findItem( OptionsMenu.DESELECT_ALL );
         deselAllItem.setEnabled( someSelected );
         
         final MenuItem invSelItem = menu.findItem( OptionsMenu.INVERT_SELECTION );
         invSelItem.setEnabled( !allSelected && someSelected );
      }
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      final SelectableTasksListFragmentAdapter adapter = getListAdapter();
      
      if ( adapter != null )
      {
         // Handle item selection
         switch ( item.getItemId() )
         {
            case OptionsMenu.SELECT_ALL:
               adapter.changeSelection( SelectableTasksListFragmentAdapter.SEL_MODE_ALL );
               return true;
               
            case OptionsMenu.DESELECT_ALL:
               adapter.changeSelection( SelectableTasksListFragmentAdapter.SEL_MODE_NONE );
               return true;
               
            case OptionsMenu.INVERT_SELECTION:
               adapter.changeSelection( SelectableTasksListFragmentAdapter.SEL_MODE_INVERT );
               return true;
               
            case OptionsMenu.DO_EDIT:
               listener.onEditSelectedTasks( getListAdapter().getSelectedTasks() );
               return true;
               
            case OptionsMenu.COMPLETE:
               new AlertDialog.Builder( getActivity() ).setMessage( getString( R.string.select_multiple_tasks_dlg_complete,
                                                                               adapter.getSelectedCount(),
                                                                               getResources().getQuantityString( R.plurals.g_task,
                                                                                                                 adapter.getSelectedCount() ) ) )
                                                       .setPositiveButton( R.string.btn_complete,
                                                                           new OnClickListener()
                                                                           {
                                                                              @Override
                                                                              public void onClick( DialogInterface dialog,
                                                                                                   int which )
                                                                              {
                                                                                 listener.onCompleteSelectedTasks( getListAdapter().getSelectedTasks() );
                                                                              }
                                                                           } )
                                                       .setNegativeButton( R.string.btn_cancel,
                                                                           null )
                                                       .show();
               return true;
               
            case OptionsMenu.UNCOMPLETE:
               new AlertDialog.Builder( getActivity() ).setMessage( getString( R.string.select_multiple_tasks_dlg_uncomplete,
                                                                               adapter.getSelectedCount(),
                                                                               getResources().getQuantityString( R.plurals.g_task,
                                                                                                                 adapter.getSelectedCount() ) ) )
                                                       .setPositiveButton( R.string.btn_uncomplete,
                                                                           new OnClickListener()
                                                                           {
                                                                              @Override
                                                                              public void onClick( DialogInterface dialog,
                                                                                                   int which )
                                                                              {
                                                                                 listener.onUncompleteSelectedTasks( getListAdapter().getSelectedTasks() );
                                                                              }
                                                                           } )
                                                       .setNegativeButton( R.string.btn_cancel,
                                                                           null )
                                                       .show();
               return true;
               
            case OptionsMenu.POSTPONE:
               new AlertDialog.Builder( getActivity() ).setMessage( getString( R.string.select_multiple_tasks_dlg_postpone,
                                                                               adapter.getSelectedCount(),
                                                                               getResources().getQuantityString( R.plurals.g_task,
                                                                                                                 adapter.getSelectedCount() ) ) )
                                                       .setPositiveButton( R.string.btn_postpone,
                                                                           new OnClickListener()
                                                                           {
                                                                              @Override
                                                                              public void onClick( DialogInterface dialog,
                                                                                                   int which )
                                                                              {
                                                                                 listener.onPostponeSelectedTasks( getListAdapter().getSelectedTasks() );
                                                                              }
                                                                           } )
                                                       .setNegativeButton( R.string.btn_cancel,
                                                                           null )
                                                       .show();
            case OptionsMenu.DELETE:
               new AlertDialog.Builder( getActivity() ).setMessage( getString( R.string.select_multiple_tasks_dlg_delete,
                                                                               adapter.getSelectedCount(),
                                                                               getResources().getQuantityString( R.plurals.g_task,
                                                                                                                 adapter.getSelectedCount() ) ) )
                                                       .setPositiveButton( R.string.btn_delete,
                                                                           new OnClickListener()
                                                                           {
                                                                              @Override
                                                                              public void onClick( DialogInterface dialog,
                                                                                                   int which )
                                                                              {
                                                                                 listener.onDeleteSelectedTasks( getListAdapter().getSelectedTasks() );
                                                                              }
                                                                           } )
                                                       .setNegativeButton( R.string.btn_cancel,
                                                                           null )
                                                       .show();
               return true;
               
            default :
               return super.onOptionsItemSelected( item );
         }
      }
      
      return super.onOptionsItemSelected( item );
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      final Checkable checkableView = ( (Checkable) info.targetView.findViewById( R.id.selectmultipletasks_listitem_selected ) );
      final ListTask task = getTask( info.position );
      
      if ( checkableView.isChecked() )
         menu.add( Menu.NONE,
                   CtxtMenu.TOGGLE_SELECTION,
                   Menu.NONE,
                   getString( R.string.select_multiple_tasks_menu_ctx_deselect,
                              task.getName() ) );
      else
         menu.add( Menu.NONE,
                   CtxtMenu.TOGGLE_SELECTION,
                   Menu.NONE,
                   getString( R.string.select_multiple_tasks_menu_ctx_select,
                              task.getName() ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.TOGGLE_SELECTION:
            toggle( info.position );
            return true;
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   public void toggle( int pos )
   {
      getListAdapter().toggleSelection( pos );
      putCurrentSelectionStateToConfig( configuration );
   }
   


   @Override
   protected int getDefaultTaskSort()
   {
      return MolokoApp.getSettings().getTaskSort();
   }
   


   @Override
   public int getTaskSortIndex( int value )
   {
      if ( value == SORT_SELECTION_VALUE )
         return SORT_SELECTION_IDX;
      else
         return super.getTaskSortIndex( value );
   }
   


   @Override
   public int getTaskSortValue( int idx )
   {
      if ( idx == SORT_SELECTION_IDX )
         return SORT_SELECTION_VALUE;
      else
         return super.getTaskSortValue( idx );
   }
   


   @Override
   protected void showChooseTaskSortDialog()
   {
      new AlertDialog.Builder( getActivity() ).setIcon( R.drawable.ic_dialog_sort )
                                              .setTitle( R.string.abstaskslist_dlg_sort_title )
                                              .setSingleChoiceItems( getListAdapter().getSelectedCount() > 0
                                                                                                            ? R.array.selectmultipletasks_sort_options
                                                                                                            : R.array.app_sort_options,
                                                                     getTaskSortIndex( getTaskSortConfiguration() ),
                                                                     defaultChooseTaskSortDialogListener )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  null )
                                              .show();
   }
   


   @Override
   protected String resolveTaskSortToSqlite( int taskSort )
   {
      if ( taskSort == SORT_SELECTION_VALUE )
         // Here we take the default task sort cause we re-sort the tasks later by their selection
         return Queries.resolveTaskSortToSqlite( getDefaultTaskSort() );
      else
         return super.resolveTaskSortToSqlite( taskSort );
   }
   


   @Override
   public boolean shouldResortTasks( int taskSort )
   {
      // we always want to sort cause the selection may changed
      // TODO: Enhancement: Think about a "dirty" flag to spare sorting.
      if ( taskSort == SORT_SELECTION_VALUE )
         return true;
      else
         return super.shouldResortTasks( taskSort );
   }
   


   @Override
   public Loader< List< SelectableListTask >> onCreateLoader( int id,
                                                              Bundle config )
   {
      showLoadingSpinner( true );
      
      final IFilter filter = config.getParcelable( Config.FILTER );
      final String selection = filter != null ? filter.getSqlSelection() : null;
      final String order = resolveTaskSortToSqlite( config.getInt( Config.TASK_SORT_ORDER ) );
      
      final SelectableListTasksLoader loader = new SelectableListTasksLoader( getActivity(),
                                                                              selection,
                                                                              order,
                                                                              config.getStringArrayList( Config.SELECT_STATE ) );
      
      loader.setUpdateThrottle( DEFAULT_LOADER_THROTTLE_MS );
      
      return loader;
   }
   


   @Override
   protected ListAdapter createEmptyListAdapter()
   {
      notifyOptionsMenuChanged();
      
      return new SelectableTasksListFragmentAdapter( getActivity(),
                                                     R.layout.selectmultipletasks_activity_listitem );
   }
   


   @Override
   protected ListAdapter createListAdapterForResult( List< SelectableListTask > result,
                                                     IFilter filter )
   {
      final SelectableTasksListFragmentAdapter adapter = new SelectableTasksListFragmentAdapter( getActivity(),
                                                                                                 R.layout.selectmultipletasks_activity_listitem,
                                                                                                 result );
      
      adapter.setSelectionChangedListener( this );
      
      if ( getTaskSortConfiguration() == SORT_SELECTION_VALUE )
         adapter.sortBySelection();
      
      notifyOptionsMenuChanged();
      
      return adapter;
   }
   


   @Override
   public SelectableTasksListFragmentAdapter getListAdapter()
   {
      return (SelectableTasksListFragmentAdapter) super.getListAdapter();
   }
   


   @Override
   protected void notifyDataSetChanged()
   {
      if ( getListAdapter() != null )
         getListAdapter().notifyDataSetChanged();
   }
   


   @Override
   public void onSelectionChanged()
   {
      notifyOptionsMenuChanged();
   }
}
