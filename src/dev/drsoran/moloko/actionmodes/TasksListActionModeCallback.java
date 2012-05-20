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

import android.content.Context;
import android.util.Pair;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.listener.ITasksListActionModeListener;
import dev.drsoran.moloko.adapters.ISelectableAdapter;
import dev.drsoran.rtm.Task;


public class TasksListActionModeCallback extends
         BaseSelectableActionModeCallback< Task >
{
   ITasksListActionModeListener listener;
   
   
   
   public TasksListActionModeCallback( Context context,
      ISelectableAdapter< Task > adapter )
   {
      super( context, adapter );
   }
   
   
   
   public void setTasksListActionModeListener( ITasksListActionModeListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      super.onCreateActionMode( mode, menu );
      mode.getMenuInflater().inflate( R.menu.taskslist_actionmode, menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      super.onPrepareActionMode( mode, menu );
      
      final ISelectableAdapter< Task > adapter = getAdapter();
      final boolean someSelected = adapter.areSomeSelected();
      
      menu.setGroupVisible( R.id.menu_group_taskslist_actionmode, someSelected );
      
      if ( someSelected )
      {
         final Context context = getContext();
         final int selectedCount = adapter.getSelectedCount();
         final Pair< Integer, Integer > selectedCompletedUncompletedCount = getSelectedCompletedUncompletedCount( adapter );
         
         menu.findItem( R.id.menu_edit_selected )
             .setTitle( context.getString( R.string.select_multiple_tasks_menu_opt_do_edit,
                                           selectedCount ) );
         
         // The complete task menu is only shown if all selected tasks are uncompleted
         menu.findItem( R.id.menu_complete_selected_tasks )
             .setVisible( someSelected
                && selectedCompletedUncompletedCount.second.intValue() == selectedCount )
             .setTitle( context.getString( R.string.select_multiple_tasks_menu_opt_complete,
                                           selectedCount ) );
         
         // The incomplete task menu is only shown if all selected tasks are completed
         menu.findItem( R.id.menu_uncomplete_selected_tasks )
             .setVisible( someSelected
                && selectedCompletedUncompletedCount.first.intValue() == selectedCount )
             .setTitle( context.getString( R.string.select_multiple_tasks_menu_opt_uncomplete,
                                           selectedCount ) );
         
         menu.findItem( R.id.menu_postpone_selected_tasks )
             .setTitle( context.getString( R.string.select_multiple_tasks_menu_opt_postpone,
                                           selectedCount ) );
         
         menu.findItem( R.id.menu_delete_selected )
             .setTitle( context.getString( R.string.select_multiple_tasks_menu_opt_delete,
                                           selectedCount ) );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_complete_selected_tasks:
            if ( listener != null )
               listener.onCompleteTasks( new ArrayList< Task >( getAdapter().getSelectedItems() ) );
            return true;
            
         case R.id.menu_uncomplete_selected_tasks:
            if ( listener != null )
               listener.onIncompleteTasks( new ArrayList< Task >( getAdapter().getSelectedItems() ) );
            return true;
            
         case R.id.menu_edit_selected:
            if ( listener != null )
               listener.onEditTasks( new ArrayList< Task >( getAdapter().getSelectedItems() ) );
            return true;
            
         case R.id.menu_postpone_selected_tasks:
            if ( listener != null )
               listener.onPostponeTasks( new ArrayList< Task >( getAdapter().getSelectedItems() ) );
            return true;
            
         case R.id.menu_delete_selected:
            if ( listener != null )
               listener.onDeleteTasks( new ArrayList< Task >( getAdapter().getSelectedItems() ) );
            return true;
            
         default :
            return super.onActionItemClicked( mode, item );
      }
   }
   
   
   
   private static Pair< Integer, Integer > getSelectedCompletedUncompletedCount( ISelectableAdapter< Task > adapter )
   {
      int selCompl = 0, selUncompl = 0;
      
      for ( Task task : adapter.getSelectedItems() )
      {
         if ( task.getCompleted() != null )
            ++selCompl;
         else
            ++selUncompl;
      }
      
      return Pair.create( Integer.valueOf( selCompl ),
                          Integer.valueOf( selUncompl ) );
   }
}
