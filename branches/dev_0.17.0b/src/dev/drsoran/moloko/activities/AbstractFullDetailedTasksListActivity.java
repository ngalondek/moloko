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

import java.util.Collection;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.QuickAddTaskActionModeCallback;
import dev.drsoran.moloko.actionmodes.listener.IQuickAddTaskActionModeListener;
import dev.drsoran.moloko.actionmodes.listener.ITasksListActionModeListener;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.dialogs.AddRenameListDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChooseTagsDialogFragment;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IShowTasksWithTagsListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements
         IFullDetailedTasksListFragmentListener, IShowTasksWithTagsListener,
         IQuickAddTaskActionModeListener, ITasksListActionModeListener
{
   @InstanceState( key = "ACTIONMODE_QUICK_ADD_TASK" )
   private boolean quickAddTaskActionModeActive;
   
   private ActionMode activeActionMode;
   
   
   
   protected AbstractFullDetailedTasksListActivity()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AbstractFullDetailedTasksListActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      if ( quickAddTaskActionModeActive )
      {
         showQuickAddTaskInput();
      }
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final MenuInflater inflater = getSupportMenuInflater();
      
      if ( isWritableAccess() )
      {
         inflater.inflate( R.menu.taskslist_activity_rwd, menu );
      }
      
      inflater.inflate( R.menu.sync, menu );
      inflater.inflate( R.menu.search, menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_quick_add_task:
            showQuickAddTaskInput();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onActionModeStarted( ActionMode mode )
   {
      activeActionMode = mode;
      super.onActionModeStarted( mode );
   }
   
   
   
   @Override
   public void onActionModeFinished( ActionMode mode )
   {
      activeActionMode = null;
      quickAddTaskActionModeActive = false;
      super.onActionModeFinished( mode );
   }
   
   
   
   @Override
   public void onQuickAddAddNewTask( Bundle parsedValues )
   {
      activeActionMode.finish();
      startActivity( Intents.createAddTaskIntent( this, parsedValues ) );
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
   public void onEditTasks( Collection< ? extends Task > tasks )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void onCompleteTasks( Collection< ? extends Task > tasks )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void onIncompleteTasks( Collection< ? extends Task > tasks )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void onPostponeTasks( Collection< ? extends Task > tasks )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void onDeleteTasks( Collection< ? extends Task > tasks )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void onFinishingActionMode()
   {
      // TODO Auto-generated method stub
      
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
      if ( activeActionMode != null )
      {
         throw new IllegalStateException( "ActionMode already started." );
      }
      
      IFilter filter = getConfiguredFilter();
      if ( !( filter instanceof RtmSmartFilter ) )
      {
         filter = null;
      }
      
      startActionMode( new QuickAddTaskActionModeCallback( this,
                                                           (RtmSmartFilter) filter ) );
      quickAddTaskActionModeActive = true;
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
