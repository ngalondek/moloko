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

import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.SelectableListTask;


public class SelectMultipleTasksActivity extends TasksListActivity
{
   private final static String TAG = "Moloko."
      + SelectMultipleTasksActivity.class.getSimpleName();
   
   
   private static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      public final static int MENU_ORDER = 0;
      
      public final static int SELECT_ALL = START_IDX + 0;
      
      public final static int DESELECT_ALL = START_IDX + 1;
      
      public final static int INVERT_SELECTION = START_IDX + 2;
      
      public final static int SORT = START_IDX + 3;
      
      public final static int DO_EDIT = START_IDX + 4;
      
      public final static int COMPLETE = START_IDX + 5;
      
      public final static int UNCOMPLETE = START_IDX + 6;
      
      public final static int POSTPONE = START_IDX + 7;
      
      public final static int DELETE = START_IDX + 8;
   }
   

   private static class CtxtMenu
   {
      public final static int TOGGLE_SELECTION = 0;
   }
   
   private final static String CHECK_STATE = "check_state";
   
   private final static int SORT_SELECTION_IDX = TaskSortPreference.SORT_ORDER_VALUES.length;
   
   private final static int SORT_SELECTION_VALUE = TAG.hashCode();
   
   

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SELECT_ALL,
                OptionsMenu.MENU_ORDER,
                R.string.select_multiple_tasks_menu_opt_select_all )
          .setIcon( R.drawable.ic_menu_select_all_tasks );
      
      menu.add( Menu.NONE,
                OptionsMenu.DESELECT_ALL,
                OptionsMenu.MENU_ORDER,
                R.string.select_multiple_tasks_menu_opt_unselect_all )
          .setIcon( R.drawable.ic_menu_select_no_tasks );
      
      menu.add( Menu.NONE,
                OptionsMenu.INVERT_SELECTION,
                OptionsMenu.MENU_ORDER,
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
         
         addOptionalMenuItem( menu,
                              OptionsMenu.SORT,
                              getString( R.string.abstaskslist_menu_opt_sort ),
                              OptionsMenu.MENU_ORDER + 1,
                              R.drawable.ic_menu_sort,
                              adapter.getCount() > 1 );
         
         addOptionalMenuItem( menu,
                              OptionsMenu.DO_EDIT,
                              getString( R.string.select_multiple_tasks_menu_opt_do_edit,
                                         selCnt ),
                              OptionsMenu.MENU_ORDER + 2,
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
         addOptionalMenuItem( menu,
                              OptionsMenu.COMPLETE,
                              getString( R.string.select_multiple_tasks_menu_opt_complete,
                                         selCnt ),
                              OptionsMenu.MENU_ORDER + 3,
                              R.drawable.ic_menu_complete,
                              someSelected && selUncompl == selCnt );
         
         // The uncomplete task menu is only shown if all selected tasks are completed
         addOptionalMenuItem( menu,
                              OptionsMenu.UNCOMPLETE,
                              getString( R.string.select_multiple_tasks_menu_opt_uncomplete,
                                         selCnt ),
                              OptionsMenu.MENU_ORDER + 3,
                              R.drawable.ic_menu_incomplete,
                              someSelected && selCompl == selCnt );
         
         addOptionalMenuItem( menu,
                              OptionsMenu.POSTPONE,
                              getString( R.string.select_multiple_tasks_menu_opt_postpone,
                                         selCnt ),
                              OptionsMenu.MENU_ORDER + 3,
                              R.drawable.ic_menu_postponed,
                              someSelected );
         
         addOptionalMenuItem( menu,
                              OptionsMenu.DELETE,
                              getString( R.string.select_multiple_tasks_menu_opt_delete,
                                         selCnt ),
                              OptionsMenu.MENU_ORDER + 3,
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
               new AlertDialog.Builder( this ).setIcon( R.drawable.ic_dialog_sort )
                                              .setTitle( R.string.abstaskslist_dlg_sort_title )
                                              .setSingleChoiceItems( adapter.getSelectedCount() > 0
                                                                                                   ? R.array.selectmultipletasks_sort_options
                                                                                                   : R.array.app_sort_options,
                                                                     getTaskSortIndex( getTaskSort() ),
                                                                     this )
                                              .setNegativeButton( R.string.btn_cancel,
                                                                  null )
                                              .show();
               
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
   


   @Override
   public SelectMultipleTasksListAdapter getListAdapter()
   {
      return (SelectMultipleTasksListAdapter) super.getListAdapter();
   }
   


   @Override
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
      ( (Checkable) v.findViewById( R.id.taskslist_listitem_priority ) ).toggle();
      toggleSelection( position );
   }
   


   @Override
   protected ListAdapter createListAdapter( AsyncFillListResult result )
   {
      final List< ListTask > selTasks = result != null
                                                      ? SelectableListTask.asListTaskList( result.tasks )
                                                      : Collections.< ListTask > emptyList();
      
      return new SelectMultipleTasksListAdapter( this,
                                                 R.layout.selectmultipletasks_activity_listitem,
                                                 selTasks,
                                                 result != null
                                                               ? result.filter
                                                               : new RtmSmartFilter( Strings.EMPTY_STRING ) );
   }
   


   @Override
   protected void beforeQueryTasksAsync( Bundle configuration )
   {
      super.beforeQueryTasksAsync( configuration );
      
      final SelectMultipleTasksListAdapter adapter = getListAdapter();
      if ( adapter != null )
         configuration.putStringArrayList( CHECK_STATE,
                                           adapter.getSelectedTaskIds() );
   }
   


   @Override
   protected void setTasksResult( AsyncFillListResult result )
   {
      super.setTasksResult( result );
      
      if ( result.configuration.containsKey( CHECK_STATE ) )
         getListAdapter().setSelectedTaskIds( result.configuration.getStringArrayList( CHECK_STATE ) );
   }
   


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
   


   @Override
   protected void setTaskSort( int taskSort, boolean refillList )
   {
      if ( taskSort == SORT_SELECTION_VALUE )
      {
         getListAdapter().sortBySelection();
         super.setTaskSort( taskSort, false );
      }
      else
         super.setTaskSort( taskSort, refillList );
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
