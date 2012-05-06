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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.dialogs.AddRenameListDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChooseTagsDialogFragment;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IShowTasksWithTagsListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.MolokoMenuItemBuilder;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements
         IFullDetailedTasksListFragmentListener, IShowTasksWithTagsListener
{
   private static class OptionsMenu
   {
      public final static int QUICK_ADD_TASK = R.id.menu_quick_add_task;
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final MolokoMenuItemBuilder builder = new MolokoMenuItemBuilder();
      
      builder.setItemId( OptionsMenu.QUICK_ADD_TASK )
             .setTitle( getString( R.string.app_task_add ) )
             .setIconId( R.drawable.ic_menu_add_task )
             .setOrder( MenuCategory.CONTAINER )
             .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_ALWAYS )
             .setShow( !AccountUtils.isReadOnlyAccess( this ) )
             .build( menu );
      
      MolokoMenuItemBuilder.newSearchMenuItem( this )
                           .setOrder( MenuCategory.ALTERNATIVE )
                           .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_IF_ROOM )
                           .build( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.QUICK_ADD_TASK:
            showQuickAddTaskInput();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onOpenTask( int pos )
   {
      startActivity( Intents.createOpenTaskIntent( this, getTask( pos ).getId() ) );
   }
   
   
   
   @Override
   public void onSelectTasks()
   {
      startActivity( Intents.createSelectMultipleTasksIntent( this,
                                                              getConfiguredFilter(),
                                                              getTaskSort() ) );
   }
   
   
   
   @Override
   public void onEditTask( int pos )
   {
      startActivity( Intents.createEditTaskIntent( this, getTask( pos ) ) );
   }
   
   
   
   @Override
   public void onCompleteTask( int pos )
   {
      final Task task = getTask( pos );
      final ApplyChangesInfo modifications = TaskEditUtils.setTaskCompletion( this,
                                                                              task,
                                                                              true );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onIncompleteTask( int pos )
   {
      final Task task = getTask( pos );
      final ApplyChangesInfo modifications = TaskEditUtils.setTaskCompletion( this,
                                                                              task,
                                                                              false );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onPostponeTask( int pos )
   {
      final Task task = getTask( pos );
      final ApplyChangesInfo modifications = TaskEditUtils.postponeTask( this,
                                                                         task );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onDeleteTask( int pos )
   {
      final Task task = getTask( pos );
      UIUtils.showDeleteElementDialog( this, task.getName(), task.getId() );
   }
   
   
   
   @Override
   public void onOpenList( int pos, String listId )
   {
      startActivity( Intents.createOpenListIntentById( this, listId, null ) );
   }
   
   
   
   @Override
   public void onOpenLocation( int pos, String locationId )
   {
      startActivity( Intents.createOpenLocationIntentByName( this,
                                                             getTask( pos ).getLocationName() ) );
   }
   
   
   
   @Override
   public final void onShowTasksWithTags( List< String > tags )
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
   
   
   
   /*
    * Callback from ChooseTagsDialogFragment after choosing tags and a logical operation on them.
    */
   @Override
   public final void onShowTasksWithTags( List< String > tags,
                                          LogicalOperation operation )
   {
      final String logOpString = determineLogicalOperationString( operation );
      onOpenChoosenTags( tags, logOpString );
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
         case R.id.dlg_delete_element:
            if ( which == Dialog.BUTTON_POSITIVE )
               deleteTaskImpl( tag );
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, tag, which );
            break;
      }
   }
   
   
   
   protected void onOpenChoosenTags( List< String > tags,
                                     String logicalOperation )
   {
      startActivity( Intents.createOpenTagsIntent( this, tags, logicalOperation ) );
   }
   
   
   
   private void deleteTaskImpl( String taskId )
   {
      final Task task = getTask( taskId );
      final ApplyChangesInfo modifications = TaskEditUtils.deleteTask( this,
                                                                       task );
      applyModifications( modifications );
      
   }
   
   
   
   private void showQuickAddTaskInput()
   {
      showQuickAddTaskActionBarFragment( !isQuickAddTaskFragmentOpen() );
   }
   
   
   
   private static String determineLogicalOperationString( LogicalOperation operation )
   {
      final String logOpString;
      
      switch ( operation )
      {
         case AND:
            logOpString = RtmSmartFilterLexer.AND_LIT;
            break;
         
         case OR:
            logOpString = RtmSmartFilterLexer.OR_LIT;
            break;
         
         default :
            logOpString = null;
            break;
      }
      return logOpString;
   }
   
   
   
   protected void showAddListDialog()
   {
      final Bundle config = new Bundle();
      config.putParcelable( AddRenameListDialogFragment.Config.FILTER,
                            getConfiguredFilter() );
      
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      UIUtils.showDialogFragment( this,
                                  dialogFragment,
                                  String.valueOf( R.id.frag_add_rename_list ) );
   }
   
   
   
   private void showChooseTagsDialog( List< String > tags )
   {
      ChooseTagsDialogFragment.show( this, tags );
   }
}
