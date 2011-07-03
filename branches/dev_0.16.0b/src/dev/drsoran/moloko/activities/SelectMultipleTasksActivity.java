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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Checkable;
import android.widget.ListView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.SelectMultipleTasksListAdapter;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.SelectableListTask;


// TODO: Repair
public class SelectMultipleTasksActivity extends TasksListActivity
{
   private final static String TAG = "Moloko."
      + SelectMultipleTasksActivity.class.getSimpleName();
   
   
   private static class OptionsMenu
   {
      public final static int SELECT_ALL = R.id.menu_select_all;
      
      public final static int DESELECT_ALL = R.id.menu_deselect_all;
      
      public final static int INVERT_SELECTION = R.id.menu_invert_selection;
      
      public final static int SORT = R.id.menu_sort;
      
      public final static int DO_EDIT = R.id.menu_edit_selected_tasks;
      
      public final static int COMPLETE = R.id.menu_complete_selected_tasks;
      
      public final static int UNCOMPLETE = R.id.menu_uncomplete_selected_tasks;
      
      public final static int POSTPONE = R.id.menu_postpone_selected_tasks;
      
      public final static int DELETE = R.id.menu_delete_selected_tasks;
   }
   
   
   private static class CtxtMenu
   {
      public final static int TOGGLE_SELECTION = R.id.ctx_menu_toggle_selection;
   }
   
   private final static String CHECK_STATE = "check_state";
   
   private final static int SORT_SELECTION_IDX = TaskSortPreference.SORT_ORDER_VALUES.length;
   
   private final static int SORT_SELECTION_VALUE = TAG.hashCode();
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
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
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      final SelectMultipleTasksListAdapter adapter = getListAdapter();
      
      if ( adapter != null )
      {
         final boolean allSelected = adapter.areAllSelected();
         final boolean someSelected = adapter.areSomeSelected();
         final List< SelectableListTask > tasks = adapter.getSelectedTasks();
         final int selCnt = tasks.size();
         
         UIUtils.addOptionalMenuItem( menu,
                                      OptionsMenu.SORT,
                                      getString( R.string.abstaskslist_menu_opt_sort ),
                                      Menu.CATEGORY_CONTAINER,
                                      R.drawable.ic_menu_sort,
                                      adapter.getCount() > 1 );
         
         UIUtils.addOptionalMenuItem( menu,
                                      OptionsMenu.DO_EDIT,
                                      getString( R.string.select_multiple_tasks_menu_opt_do_edit,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_edit,
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
         UIUtils.addOptionalMenuItem( menu,
                                      OptionsMenu.COMPLETE,
                                      getString( R.string.select_multiple_tasks_menu_opt_complete,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_complete,
                                      someSelected && selUncompl == selCnt );
         
         // The uncomplete task menu is only shown if all selected tasks are completed
         UIUtils.addOptionalMenuItem( menu,
                                      OptionsMenu.UNCOMPLETE,
                                      getString( R.string.select_multiple_tasks_menu_opt_uncomplete,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_incomplete,
                                      someSelected && selCompl == selCnt );
         
         UIUtils.addOptionalMenuItem( menu,
                                      OptionsMenu.POSTPONE,
                                      getString( R.string.select_multiple_tasks_menu_opt_postpone,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_postponed,
                                      someSelected );
         
         UIUtils.addOptionalMenuItem( menu,
                                      OptionsMenu.DELETE,
                                      getString( R.string.select_multiple_tasks_menu_opt_delete,
                                                 selCnt ),
                                      Menu.CATEGORY_SECONDARY,
                                      R.drawable.ic_menu_trash,
                                      someSelected );
         
         final MenuItem selAllItem = menu.findItem( OptionsMenu.SELECT_ALL );
         selAllItem.setEnabled( !allSelected );
         
         final MenuItem deselAllItem = menu.findItem( OptionsMenu.DESELECT_ALL );
         deselAllItem.setEnabled( someSelected );
         
         final MenuItem invSelItem = menu.findItem( OptionsMenu.INVERT_SELECTION );
         invSelItem.setEnabled( !allSelected && someSelected );
         
         return true;
      }
      
      return false;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      final SelectMultipleTasksListAdapter adapter = getListAdapter();
      
      if ( adapter != null )
      {
         // Handle item selection
         switch ( item.getItemId() )
         {
            case OptionsMenu.SELECT_ALL:
               adapter.changeSelection( SelectMultipleTasksListAdapter.SEL_MODE_ALL );
               return true;
               
            case OptionsMenu.DESELECT_ALL:
               adapter.changeSelection( SelectMultipleTasksListAdapter.SEL_MODE_NONE );
               return true;
               
            case OptionsMenu.INVERT_SELECTION:
               adapter.changeSelection( SelectMultipleTasksListAdapter.SEL_MODE_INVERT );
               return true;
               
            case OptionsMenu.SORT:
               // new AlertDialog.Builder( this ).setIcon( R.drawable.ic_dialog_sort )
               // .setTitle( R.string.abstaskslist_dlg_sort_title )
               // .setSingleChoiceItems( adapter.getSelectedCount() > 0
               // ? R.array.selectmultipletasks_sort_options
               // : R.array.app_sort_options,
               // getTaskSortIndex( getTaskSort() ),
               // this )
               // .setNegativeButton( R.string.btn_cancel,
               // null )
               // .show();
               
               return true;
               
            case OptionsMenu.DO_EDIT:
               onEditSelectedTasks();
               return true;
               
            case OptionsMenu.COMPLETE:
               new AlertDialog.Builder( this ).setMessage( getString( R.string.select_multiple_tasks_dlg_complete,
                                                                      adapter.getSelectedCount(),
                                                                      getResources().getQuantityString( R.plurals.g_task,
                                                                                                        adapter.getSelectedCount() ) ) )
                                              .setPositiveButton( R.string.btn_complete,
                                                                  new OnClickListener()
                                                                  {
                                                                     public void onClick( DialogInterface dialog,
                                                                                          int which )
                                                                     {
                                                                        onSelectedTasksCompletion( true );
                                                                     }
                                                                  } )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  null )
                                              .show();
               return true;
               
            case OptionsMenu.UNCOMPLETE:
               new AlertDialog.Builder( this ).setMessage( getString( R.string.select_multiple_tasks_dlg_uncomplete,
                                                                      adapter.getSelectedCount(),
                                                                      getResources().getQuantityString( R.plurals.g_task,
                                                                                                        adapter.getSelectedCount() ) ) )
                                              .setPositiveButton( R.string.btn_uncomplete,
                                                                  new OnClickListener()
                                                                  {
                                                                     public void onClick( DialogInterface dialog,
                                                                                          int which )
                                                                     {
                                                                        onSelectedTasksCompletion( false );
                                                                     }
                                                                  } )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  null )
                                              .show();
               return true;
               
            case OptionsMenu.POSTPONE:
               new AlertDialog.Builder( this ).setMessage( getString( R.string.select_multiple_tasks_dlg_postpone,
                                                                      adapter.getSelectedCount(),
                                                                      getResources().getQuantityString( R.plurals.g_task,
                                                                                                        adapter.getSelectedCount() ) ) )
                                              .setPositiveButton( R.string.btn_postpone,
                                                                  new OnClickListener()
                                                                  {
                                                                     public void onClick( DialogInterface dialog,
                                                                                          int which )
                                                                     {
                                                                        onSelectedTasksPostpone();
                                                                     }
                                                                  } )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  null )
                                              .show();
            case OptionsMenu.DELETE:
               new AlertDialog.Builder( this ).setMessage( getString( R.string.select_multiple_tasks_dlg_delete,
                                                                      adapter.getSelectedCount(),
                                                                      getResources().getQuantityString( R.plurals.g_task,
                                                                                                        adapter.getSelectedCount() ) ) )
                                              .setPositiveButton( R.string.btn_delete,
                                                                  new OnClickListener()
                                                                  {
                                                                     public void onClick( DialogInterface dialog,
                                                                                          int which )
                                                                     {
                                                                        onSelectedTasksDelete();
                                                                     }
                                                                  } )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  null )
                                              .show();
               return true;
               
            default :
               break;
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
      final Checkable checkableView = ( (Checkable) info.targetView.findViewById( R.id.taskslist_listitem_priority ) );
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
            ( (Checkable) info.targetView.findViewById( R.id.taskslist_listitem_priority ) ).toggle();
            return true;
         default :
            return super.onContextItemSelected( item );
      }
   }
   
   
   
   public SelectMultipleTasksListAdapter getListAdapter()
   {
      // return (SelectMultipleTasksListAdapter) super.getListAdapter();
      return null;
   }
   
   
   
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
      ( (Checkable) v.findViewById( R.id.taskslist_listitem_priority ) ).toggle();
      toggleSelection( position );
   }
   
   
   
   // @Override
   // protected ListAdapter createListAdapter( AsyncFillListResult result )
   // {
   // final List< ListTask > selTasks = result != null
   // ? SelectableListTask.asListTaskList( result.tasks )
   // : Collections.< ListTask > emptyList();
   //
   // return new SelectMultipleTasksListAdapter( this,
   // R.layout.selectmultipletasks_activity_listitem,
   // selTasks,
   // result != null
   // ? result.filter
   // : new RtmSmartFilter( Strings.EMPTY_STRING ) );
   // }
   //
   //
   //
   // @Override
   // protected void beforeQueryTasksAsync( Bundle configuration )
   // {
   // super.beforeQueryTasksAsync( configuration );
   //
   // final SelectMultipleTasksListAdapter adapter = getListAdapter();
   // if ( adapter != null )
   // configuration.putStringArrayList( CHECK_STATE,
   // adapter.getSelectedTaskIds() );
   // }
   //
   //
   //
   // @Override
   // protected void setTasksResult( AsyncFillListResult result )
   // {
   // super.setTasksResult( result );
   //
   // if ( result.configuration.containsKey( CHECK_STATE ) )
   // getListAdapter().setSelectedTaskIds( result.configuration.getStringArrayList( CHECK_STATE ) );
   // }
   //
   
   @Override
   protected int getTaskSortIndex( int value )
   {
      if ( value == SORT_SELECTION_VALUE )
         return SORT_SELECTION_IDX;
      else
         return super.getTaskSortIndex( value );
   }
   
   
   
   @Override
   protected int getTaskSortValue( int idx )
   {
      if ( idx == SORT_SELECTION_IDX )
         return SORT_SELECTION_VALUE;
      else
         return super.getTaskSortValue( idx );
   }
   
   
   
   protected void setTaskSort( int taskSort, boolean refillList )
   {
      // if ( taskSort == SORT_SELECTION_VALUE )
      // {
      // getListAdapter().sortBySelection();
      // super.setTaskSort( taskSort, false );
      // }
      // else
      // super.setTaskSort( taskSort, refillList );
   }
   
   
   
   @Override
   protected boolean isSameTaskSortLikeCurrent( int sortOrder )
   {
      // we always want to sort cause the selection may changed
      // TODO: Enhancement: Think about a "dirty" flag to spare sorting.
      if ( sortOrder == SORT_SELECTION_VALUE )
         return false;
      else
         return super.isSameTaskSortLikeCurrent( sortOrder );
   }
   
   
   
   private void toggleSelection( int pos )
   {
      final SelectMultipleTasksListAdapter adapter = getListAdapter();
      adapter.toggleSelection( pos );
   }
   
   
   
   private void onEditSelectedTasks()
   {
      final int selCnt = getListAdapter().getSelectedCount();
      if ( selCnt > 0 )
         if ( selCnt > 1 )
            startActivityForResult( Intents.createEditMultipleTasksIntent( this,
                                                                           getListAdapter().getSelectedTaskIds() ),
                                    EditMultipleTasksActivity.REQ_EDIT_TASK );
         else
            startActivityForResult( Intents.createEditTaskIntent( this,
                                                                  getListAdapter().getSelectedTaskIds()
                                                                                  .get( 0 ) ),
                                    TaskEditActivity.REQ_EDIT_TASK );
   }
   
   
   
   private void onSelectedTasksCompletion( boolean complete )
   {
      TaskEditUtils.setTasksCompletion( this,
                                        getListAdapter().getSelectedTasks(),
                                        complete );
   }
   
   
   
   private void onSelectedTasksPostpone()
   {
      TaskEditUtils.postponeTasks( this, getListAdapter().getSelectedTasks() );
   }
   
   
   
   private void onSelectedTasksDelete()
   {
      TaskEditUtils.deleteTasks( this, getListAdapter().getSelectedTasks() );
   }
}
