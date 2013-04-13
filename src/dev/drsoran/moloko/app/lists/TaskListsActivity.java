/*
 * Copyright (c) 2012 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.app.lists;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoEditFragmentActivity;
import dev.drsoran.moloko.app.content.ApplyContentChangesInfo;
import dev.drsoran.moloko.app.listsedit.AddRenameListDialogFragment;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.IEditFragment;
import dev.drsoran.moloko.ui.fragments.listeners.IMolokoEditDialogFragmentListener;
import dev.drsoran.moloko.util.RtmListEditUtils;


public class TaskListsActivity extends MolokoEditFragmentActivity implements
         ITaskListsFragmentListener, IMolokoEditDialogFragmentListener
{
   private final static class Config
   {
      public final static String LIST_TO_DELETE = "list_to_delete";
   }
   
   @InstanceState( key = Config.LIST_TO_DELETE,
                   defaultValue = InstanceState.NULL )
   private ITasksList listToDelete;
   
   
   
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
         getSupportMenuInflater().inflate( R.menu.tasklists_activity_rwd, menu );
      }
      else
      {
         getSupportMenuInflater().inflate( R.menu.tasklists_activity, menu );
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
      final ITasksList tasksList = getList( pos );
      
      // Check if the smart filter could be parsed. Otherwise
      // we do not fire the intent.
      if ( isSmartFilterValid( tasksList.getSmartFilter() ) )
      {
         final Intent intent = Intents.createOpenListIntent( this,
                                                             tasksList,
                                                             null );
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
      final ITasksList list = getList( pos );
      setListToDelete( list );
      
      UiUtils.showDeleteElementDialog( this, list.getName() );
   }
   
   
   
   @Override
   public void renameList( int pos )
   {
      showRenameListDialog( getList( pos ) );
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
   
   
   
   private void showRenameListDialog( ITasksList list )
   {
      createAddRenameListDialogFragment( createRenameListFragmentConfig( list ) );
   }
   
   
   
   private Bundle createRenameListFragmentConfig( ITasksList list )
   {
      final Bundle config = new Bundle();
      
      config.putParcelable( Intents.Extras.KEY_LIST, list );
      if ( list.isSmartList() )
      {
         config.putParcelable( Intents.Extras.KEY_FILTER, list.getRtmList()
                                                              .getSmartFilter() );
      }
      
      return config;
   }
   
   
   
   private void showAddListDialog()
   {
      createAddRenameListDialogFragment( Bundle.EMPTY );
   }
   
   
   
   private ITasksList getList( int pos )
   {
      final TaskListsFragment taskListsFragment = (TaskListsFragment) getSupportFragmentManager().findFragmentById( R.id.frag_tasklists );
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
         final ApplyContentChangesInfo modifications = RtmListEditUtils.deleteList( this,
                                                                                    getListToDelete() );
         applyModifications( modifications );
      }
      
      setListToDelete( null );
   }
   
   
   
   private boolean isSmartFilterValid( RtmSmartFilter smartFilter )
   {
      return getAppContext().getParsingService()
                            .getRtmSmartFilterParsing()
                            .isParsableSmartFilter( smartFilter.getFilterString() );
   }
   
   
   
   private ITasksList getListToDelete()
   {
      return listToDelete;
   }
   
   
   
   private void setListToDelete( ITasksList listToDelete )
   {
      this.listToDelete = listToDelete;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_tasklists };
   }
}