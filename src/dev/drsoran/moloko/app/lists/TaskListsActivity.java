/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.lists;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoEditActivity;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.IEditFragment;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;


public class TaskListsActivity extends MolokoEditActivity implements
         ITaskListsFragmentListener, IAddRenameListFragmentListener
{
   @InstanceState( key = "list_to_delete", defaultValue = InstanceState.NULL )
   private TasksList listToDelete;
   
   
   
   public TaskListsActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskListsActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.tasklists_activity );
   }
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( isWritableAccess() )
      {
         getMenuInflater().inflate( R.menu.tasklists_activity_rwd, menu );
      }
      else
      {
         getMenuInflater().inflate( R.menu.tasklists_activity, menu );
      }
      
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_add_list:
            showAddListDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void openList( int pos )
   {
      final TasksList tasksList = getList( pos );
      
      // Check if the smart filter could be parsed. Otherwise
      // we do not fire the intent.
      final boolean fireIntent = !tasksList.isSmartList()
         || tasksList.isSmartList()
         && isSmartFilterValid( tasksList.getSmartFilter() );
      
      if ( fireIntent )
      {
         final Intent intent = Intents.createOpenListIntent( tasksList, null );
         startActivityWithHomeAction( intent, getClass() );
      }
   }
   
   
   
   @Override
   public void openChild( Intent intent )
   {
      startActivityWithHomeAction( intent, getClass() );
   }
   
   
   
   @Override
   public void deleteList( int pos )
   {
      final TasksList list = getList( pos );
      setListToDelete( list );
      
      showDeleteListDialog( list.getName() );
   }
   
   
   
   @Override
   public void renameList( int pos )
   {
      showRenameListDialog( getList( pos ) );
   }
   
   
   
   @Override
   public void onInsertNewList( TasksList tasksList )
   {
      getAppContext().getContentEditService().insertTasksList( this, tasksList );
   }
   
   
   
   @Override
   public void onRenameList( TasksList tasksList )
   {
      getAppContext().getContentEditService().updateTasksList( this, tasksList );
   }
   
   
   
   @Override
   public void onValidateDialogFragment( IEditFragment editDialogFragment )
   {
      validateFragment( editDialogFragment );
   }
   
   
   
   @Override
   public void onFinishEditDialogFragment( IEditFragment editDialogFragment )
   {
      finishFragmentEditing( editDialogFragment );
   }
   
   
   
   @Override
   public void onCancelEditDialogFragment( IEditFragment editDialogFragment )
   {
      // In case of a dialog we cannot show a cancel query since the dialog has already gone.
      editDialogFragment.onCancelEditing();
   }
   
   
   
   private void showRenameListDialog( TasksList list )
   {
      createAddRenameListDialogFragment( createRenameListFragmentConfig( list ) );
   }
   
   
   
   private Bundle createRenameListFragmentConfig( TasksList list )
   {
      final Bundle config = new Bundle( 1 );
      
      config.putSerializable( Intents.Extras.KEY_LIST, list );
      
      return config;
   }
   
   
   
   private void showAddListDialog()
   {
      createAddRenameListDialogFragment( Bundle.EMPTY );
   }
   
   
   
   private TasksList getList( int pos )
   {
      final TaskListsFragment taskListsFragment = (TaskListsFragment) getFragmentManager().findFragmentById( R.id.frag_tasklists );
      return taskListsFragment.getList( pos );
   }
   
   
   
   private void createAddRenameListDialogFragment( Bundle config )
   {
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      UiUtils.showDialogFragment( this,
                                  dialogFragment,
                                  String.valueOf( R.id.frag_add_rename_list ) );
   }
   
   
   
   @Override
   protected void handleDeleteElementDialogClick( String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         getAppContext().getContentEditService().deleteTasksList( this,
                                                                  listToDelete );
      }
      
      setListToDelete( null );
   }
   
   
   
   private boolean isSmartFilterValid( RtmSmartFilter smartFilter )
   {
      return getAppContext().getParsingService()
                            .getSmartFilterParsing()
                            .isParsableSmartFilter( smartFilter.getFilterString() );
   }
   
   
   
   private void setListToDelete( TasksList listToDelete )
   {
      this.listToDelete = listToDelete;
   }
   
   
   
   private void showDeleteListDialog( String elementName )
   {
      new AlertDialogFragment.Builder( R.id.dlg_delete_element ).setMessage( getString( R.string.phr_delete_with_name,
                                                                                        elementName )
         + "?" )
                                                                .setPositiveButton( R.string.btn_delete )
                                                                .setNegativeButton( R.string.btn_cancel )
                                                                .show( this );
   }
}
