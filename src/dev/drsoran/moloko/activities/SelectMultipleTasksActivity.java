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
import dev.drsoran.moloko.adapters.SelectableTasksListFragmentAdapter;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.SelectableListTask;


// TODO: Repair
public class SelectMultipleTasksActivity extends AbstractTasksListActivity
{
   private final static String TAG = "Moloko."
      + SelectMultipleTasksActivity.class.getSimpleName();
   
   

   public SelectableTasksListFragmentAdapter getListAdapter()
   {
      // return (SelectMultipleTasksListAdapter) super.getListAdapter();
      return null;
   }
   


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
      final SelectableTasksListFragmentAdapter adapter = getListAdapter();
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
