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

package dev.drsoran.moloko.actionmodes;

import java.util.ArrayList;

import android.util.Pair;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.listener.ITasksListActionModeListener;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.rtm.Task;


public class TasksListActionModeCallback extends
         BaseMultiChoiceModeListener< Task >
{
   private final AbstractTasksListFragment< ? > fragment;
   
   private ITasksListActionModeListener listener;
   
   
   
   public TasksListActionModeCallback( AbstractTasksListFragment< ? > fragment )
   {
      super( fragment.getMolokoListView() );
      this.fragment = fragment;
   }
   
   
   
   public void setTasksListActionModeListener( ITasksListActionModeListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      super.onCreateActionMode( mode, menu );
      mode.getMenuInflater().inflate( R.menu.taskslist_actionmode_rwd, menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      super.onPrepareActionMode( mode, menu );
      
      final int selectedCount = getListView().getCheckedItemCountSupport();
      final boolean isLoaderDataAvailable = getAdapter().getCount() > 0;
      
      final boolean showRwdItems = selectedCount > 0 && isLoaderDataAvailable
         && fragment.isWritableAccess();
      menu.setGroupVisible( R.id.menu_group_at_least_one_selected, showRwdItems );
      if ( showRwdItems )
      {
         prepareRwdActionMenu( menu );
      }
      
      final boolean showSingleSelectionItems = selectedCount == 1
         && isLoaderDataAvailable;
      menu.setGroupVisible( R.id.menu_group_single_selection,
                            showSingleSelectionItems );
      if ( showSingleSelectionItems )
      {
         fragment.prepareSingleTaskActionMenu( menu, getSelectedTask() );
      }
      
      return true;
   }
   
   
   
   private void prepareRwdActionMenu( Menu menu )
   {
      final int selectedCount = getListView().getCheckedItemCountSupport();
      final Pair< Integer, Integer > selectedCompletedUncompletedCount = getSelectedCompletedUncompletedCount();
      
      menu.findItem( R.id.menu_edit_selected )
          .setTitle( fragment.getString( R.string.select_multiple_tasks_menu_opt_do_edit,
                                         selectedCount ) );
      
      // The complete task menu is only shown if all selected tasks are uncompleted
      menu.findItem( R.id.menu_complete_selected_tasks )
          .setVisible( selectedCompletedUncompletedCount.second.intValue() == selectedCount )
          .setTitle( fragment.getString( R.string.select_multiple_tasks_menu_opt_complete,
                                         selectedCount ) );
      
      // The incomplete task menu is only shown if all selected tasks are completed
      menu.findItem( R.id.menu_uncomplete_selected_tasks )
          .setVisible( selectedCompletedUncompletedCount.first.intValue() == selectedCount )
          .setTitle( fragment.getString( R.string.select_multiple_tasks_menu_opt_uncomplete,
                                         selectedCount ) );
      
      menu.findItem( R.id.menu_postpone_selected_tasks )
          .setTitle( fragment.getString( R.string.select_multiple_tasks_menu_opt_postpone,
                                         selectedCount ) );
      
      menu.findItem( R.id.menu_delete_selected )
          .setTitle( fragment.getString( R.string.select_multiple_tasks_menu_opt_delete,
                                         selectedCount ) );
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      boolean handled = false;
      
      if ( listener != null )
      {
         handled = true;
         
         switch ( item.getItemId() )
         {
            case R.id.menu_complete_selected_tasks:
               listener.onCompleteTasks( new ArrayList< Task >( getSelectedItems() ) );
               break;
            
            case R.id.menu_uncomplete_selected_tasks:
               listener.onIncompleteTasks( new ArrayList< Task >( getSelectedItems() ) );
               break;
            
            case R.id.menu_edit_selected:
               listener.onEditTasks( new ArrayList< Task >( getSelectedItems() ) );
               break;
            
            case R.id.menu_postpone_selected_tasks:
               listener.onPostponeTasks( new ArrayList< Task >( getSelectedItems() ) );
               break;
            
            case R.id.menu_delete_selected:
               listener.onDeleteTasks( new ArrayList< Task >( getSelectedItems() ) );
               break;
            
            case R.id.menu_open_tags:
               listener.onShowTasksWithTags( getSelectedTask().getTags() );
               break;
            
            case R.id.menu_open_tasks_at_loc:
               listener.onOpenTaskLocation( getSelectedTask() );
               break;
            
            default :
               handled = false;
               break;
         }
      }
      
      if ( handled )
      {
         mode.finish();
         return true;
      }
      else
      {
         return super.onActionItemClicked( mode, item );
      }
   }
   
   
   
   private Pair< Integer, Integer > getSelectedCompletedUncompletedCount()
   {
      int selCompl = 0, selUncompl = 0;
      
      for ( Task task : getSelectedItems() )
      {
         if ( task.getCompleted() != null )
            ++selCompl;
         else
            ++selUncompl;
      }
      
      return Pair.create( Integer.valueOf( selCompl ),
                          Integer.valueOf( selUncompl ) );
   }
   
   
   
   private Task getSelectedTask()
   {
      return (Task) getListView().getCheckedItems().get( 0 );
   }
}
