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

import android.app.Dialog;
import android.os.Bundle;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.SelectableTasksListsFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.fragments.listeners.ISelectableTasksListFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.rtm.Task;


public class SelectMultipleTasksActivity extends AbstractTasksListActivity
         implements ISelectableTasksListFragmentListener
{
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setTitleWithSelectedCount( 0 );
   }
   
   
   
   @Override
   public void onOpenTask( int pos )
   {
      if ( getTasksListFragment() != null )
         getTasksListFragment().toggle( pos );
   }
   
   
   
   @Override
   public void onSelectionChanged( List< ? extends Task > selectedTasks )
   {
      setTitleWithSelectedCount( selectedTasks.size() );
   }
   
   
   
   @Override
   public void onEditSelectedTasks( List< ? extends Task > tasks )
   {
      final int selCnt = tasks.size();
      if ( selCnt > 0 )
         if ( selCnt > 1 )
            startActivity( Intents.createEditMultipleTasksIntent( this, tasks ) );
         else
            startActivity( Intents.createEditTaskIntent( this, tasks.get( 0 ) ) );
   }
   
   
   
   @Override
   public void onCompleteSelectedTasks( List< ? extends Task > tasks )
   {
      final String message = getResources().getQuantityString( R.plurals.tasks_complete,
                                                               tasks.size(),
                                                               tasks.size() );
      new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_complete ).setMessage( message )
                                                                              .setPositiveButton( R.string.btn_complete )
                                                                              .setNegativeButton( R.string.btn_cancel )
                                                                              .show( this );
   }
   
   
   
   @Override
   public void onIncompleteSelectedTasks( List< ? extends Task > tasks )
   {
      final String message = getResources().getQuantityString( R.plurals.tasks_incomplete,
                                                               tasks.size(),
                                                               tasks.size() );
      new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_incomplete ).setMessage( message )
                                                                                .setPositiveButton( R.string.btn_uncomplete )
                                                                                .setNegativeButton( R.string.btn_cancel )
                                                                                .show( this );
   }
   
   
   
   @Override
   public void onPostponeSelectedTasks( List< ? extends Task > tasks )
   {
      final String message = getResources().getQuantityString( R.plurals.tasks_postpone,
                                                               tasks.size(),
                                                               tasks.size() );
      new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_postpone ).setMessage( message )
                                                                              .setPositiveButton( R.string.btn_postpone )
                                                                              .setNegativeButton( R.string.btn_cancel )
                                                                              .show( this );
   }
   
   
   
   @Override
   public void onDeleteSelectedTasks( List< ? extends Task > tasks )
   {
      final String message = getResources().getQuantityString( R.plurals.tasks_delete,
                                                               tasks.size(),
                                                               tasks.size() );
      new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_delete ).setMessage( message )
                                                                            .setPositiveButton( R.string.btn_delete )
                                                                            .setNegativeButton( R.string.btn_cancel )
                                                                            .show( this );
   }
   
   
   
   @Override
   protected SelectableTasksListsFragment getTasksListFragment()
   {
      if ( super.getTasksListFragment() instanceof SelectableTasksListsFragment )
         return (SelectableTasksListsFragment) super.getTasksListFragment();
      else
         return null;
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
         case R.id.dlg_selectmultipletasks_complete:
            if ( which == Dialog.BUTTON_POSITIVE )
               completeSelectedTasks( getTasksListFragment().getListAdapter()
                                                            .getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_incomplete:
            if ( which == Dialog.BUTTON_POSITIVE )
               incompleteSelectedTasks( getTasksListFragment().getListAdapter()
                                                              .getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_postpone:
            if ( which == Dialog.BUTTON_POSITIVE )
               postponeSelectedTasks( getTasksListFragment().getListAdapter()
                                                            .getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_delete:
            if ( which == Dialog.BUTTON_POSITIVE )
               deleteSelectedTasks( getTasksListFragment().getListAdapter()
                                                          .getSelectedTasks() );
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, tag, which );
            break;
      }
   }
   
   
   
   private void completeSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.setTasksCompletion( this,
                                                                               tasks,
                                                                               true );
      applyModifications( modifications );
   }
   
   
   
   private void incompleteSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.setTasksCompletion( this,
                                                                               tasks,
                                                                               false );
      applyModifications( modifications );
   }
   
   
   
   private void postponeSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.postponeTasks( this,
                                                                          tasks );
      applyModifications( modifications );
   }
   
   
   
   private void deleteSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.deleteTasks( this,
                                                                        tasks );
      applyModifications( modifications );
   }
   
   
   
   private void setTitleWithSelectedCount( int cnt )
   {
      getSupportActionBar().setTitle( getString( R.string.app_task_select_multiple,
                                                 cnt ) );
   }
}
