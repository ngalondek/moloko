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

package dev.drsoran.moloko.app.taskslist.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.lists.AddRenameListDialogFragment;
import dev.drsoran.moloko.app.lists.IAddRenameListFragmentListener;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.IEditFragment;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.ui.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.ui.fragments.listeners.IMolokoEditDialogFragmentListener;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements ITasksListActionModeListener,
         IShowTasksWithTagsListener, IQuickAddTaskActionModeListener,
         ILoaderFragmentListener, IMolokoEditDialogFragmentListener,
         IAddRenameListFragmentListener
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
      finishActiveActionMode();
      startActivity( Intents.createAddTaskIntent( this, parsedValues ) );
   }
   
   
   
   @Override
   public void onOpenTask( int pos )
   {
      startActivity( Intents.createOpenTaskIntent( this, getTask( pos ).getId() ) );
   }
   
   
   
   @Override
   public final void onShowTasksWithTags( Collection< String > tags )
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
   public final void onShowTasksWithTags( Collection< String > tags,
                                          LogicalOperation operation )
   {
      final String logOpString = determineLogicalOperationString( operation );
      onOpenChoosenTags( tags, logOpString );
   }
   
   
   
   @Override
   public void onOpenTaskLocation( Task task )
   {
      startActivityPreserveHomeAction( Intents.createShowTasksWithLocationNameIntent( this,
                                                                               task.getLocationName() ) );
   }
   
   
   
   @Override
   public void onEditTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         startActivity( Intents.createEditTaskIntent( this, tasks.get( 0 ) ) );
      }
      else
      {
         startActivity( Intents.createEditMultipleTasksIntent( this, tasks ) );
      }
      
      finishActiveActionMode();
   }
   
   
   
   @Override
   public void onCompleteTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         completeSelectedTasks( tasks );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_complete,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_complete ).setMessage( message )
                                                                                 .setPositiveButton( R.string.btn_complete )
                                                                                 .setNegativeButton( R.string.btn_cancel )
                                                                                 .show( this );
      }
   }
   
   
   
   @Override
   public void onIncompleteTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         incompleteSelectedTasks( tasks );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_incomplete,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_incomplete ).setMessage( message )
                                                                                   .setPositiveButton( R.string.btn_uncomplete )
                                                                                   .setNegativeButton( R.string.btn_cancel )
                                                                                   .show( this );
      }
   }
   
   
   
   @Override
   public void onPostponeTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         postponeSelectedTasks( tasks );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_postpone,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_postpone ).setMessage( message )
                                                                                 .setPositiveButton( R.string.btn_postpone )
                                                                                 .setNegativeButton( R.string.btn_cancel )
                                                                                 .show( this );
      }
   }
   
   
   
   @Override
   public void onDeleteTasks( List< ? extends Task > tasks )
   {
      final String message;
      
      if ( tasks.size() == 1 )
      {
         final Task task = tasks.get( 0 );
         message = getString( R.string.phr_delete_with_name, task.getName() );
      }
      else
      {
         message = getResources().getQuantityString( R.plurals.tasks_delete,
                                                     tasks.size(),
                                                     tasks.size() );
      }
      
      new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_delete ).setMessage( message )
                                                                            .setPositiveButton( R.string.btn_delete )
                                                                            .setNegativeButton( R.string.btn_cancel )
                                                                            .show( this );
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
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
         case R.id.dlg_selectmultipletasks_complete:
            if ( which == Dialog.BUTTON_POSITIVE )
               completeSelectedTasks( getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_incomplete:
            if ( which == Dialog.BUTTON_POSITIVE )
               incompleteSelectedTasks( getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_postpone:
            if ( which == Dialog.BUTTON_POSITIVE )
               postponeSelectedTasks( getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_delete:
            if ( which == Dialog.BUTTON_POSITIVE )
               deleteSelectedTasks( getSelectedTasks() );
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, tag, which );
            break;
      }
      
      finishActiveActionMode();
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
   
   
   
   protected void onOpenChoosenTags( Collection< String > tags,
                                     String logicalOperation )
   {
      startActivityPreserveHomeAction( Intents.createOpenTasksWithTagsIntent( this,
                                                                     new ArrayList< String >( tags ),
                                                                     logicalOperation ) );
   }
   
   
   
   @Override
   public void onFragmentLoadStarted( int fragmentId, String fragmentTag )
   {
   }
   
   
   
   @Override
   public void onFragmentLoadFinished( int fragmentId,
                                       String fragmentTag,
                                       boolean success )
   {
      if ( success && !hasShownMultiSelectionNotification()
         && getTasksListFragment().getTaskCount() > 0 )
      {
         executeDelayed( new Runnable()
                         {
                            @Override
                            public void run()
                            {
                               showMultiSelectionNotification();
                            }
                         },
                         getResources().getInteger( R.integer.popup_notification_delay_on_ms ) );
      }
   }
   
   
   
   public FragmentTransaction addBottomFragment( Fragment fragmentToAdd )
   {
      final Fragment bottomFragment = getBottomFragment();
      final FragmentTransaction transaction = getFragmentManager().beginTransaction()
                                                                  .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
      
      if ( bottomFragment == null )
      {
         transaction.add( R.id.frag_multi_container, fragmentToAdd );
      }
      else
      {
         transaction.replace( R.id.frag_multi_container, fragmentToAdd );
      }
      
      return transaction;
   }
   
   
   
   public Fragment getBottomFragment()
   {
      return findAddedFragmentById( R.id.frag_multi_container );
   }
   
   
   
   public < T > T getBottomFragment( Class< ? > clazz )
   {
      final Fragment addedFragment = getBottomFragment();
      
      if ( addedFragment != null && addedFragment.getClass() == clazz )
      {
         @SuppressWarnings( "unchecked" )
         final T fragment = (T) addedFragment;
         return fragment;
      }
      
      return null;
   }
   
   
   
   public Fragment removeBottomFragment()
   {
      final Fragment bottomFragment = getBottomFragment();
      
      if ( bottomFragment != null )
      {
         getFragmentManager().beginTransaction()
                             .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_CLOSE )
                             .remove( bottomFragment )
                             .commit();
      }
      
      return bottomFragment;
   }
   
   
   
   @Override
   public Fragment createTasksListFragment( Bundle config )
   {
      return FullDetailedTasksListFragment.newInstance( config );
   }
   
   
   
   private void completeSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().completeTasks( this, tasks );
      
      clearListChoices();
   }
   
   
   
   private void incompleteSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().incompleteTasks( this, tasks );
      clearListChoices();
   }
   
   
   
   private void postponeSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().postponeTasks( this, tasks );
      clearListChoices();
   }
   
   
   
   private void deleteSelectedTasks( List< ? extends Task > tasks )
   {
      getAppContext().getContentEditService().deleteTasks( this, tasks );
      clearListChoices();
   }
   
   
   
   private void showQuickAddTaskInput()
   {
      if ( activeActionMode != null )
      {
         throw new IllegalStateException( "ActionMode already started." );
      }
      
      startActionMode( new QuickAddTaskActionModeCallback( this,
                                                           getActiveFilter() ) );
      quickAddTaskActionModeActive = true;
   }
   
   
   
   private boolean hasShownMultiSelectionNotification()
   {
      return getAppContext().getSettings()
                            .hasNotifiedTaskListMultiSelectionHint();
   }
   
   
   
   private void showMultiSelectionNotification()
   {
      new AlertDialogFragment.Builder( View.NO_ID ).setTitle( getString( R.string.phr_hint ) )
                                                   .setMessage( getString( R.string.abstaskslist_notif_multi_selection ) )
                                                   .setNeutralButton( android.R.string.ok )
                                                   .show( this );
      
      getAppContext().getSettings().setTaskListMultiSelectionHintNotified();
   }
   
   
   
   private static String determineLogicalOperationString( LogicalOperation operation )
   {
      final String logOpString;
      
      switch ( operation )
      {
         case AND:
            logOpString = RtmSmartFilterSyntax.AND;
            break;
         
         case OR:
            logOpString = RtmSmartFilterSyntax.OR;
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
      config.putSerializable( Intents.Extras.KEY_FILTER, getActiveFilter() );
      
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      UiUtils.showDialogFragment( this,
                                  dialogFragment,
                                  String.valueOf( R.id.frag_add_rename_list ) );
   }
   
   
   
   private void showChooseTagsDialog( Collection< String > tags )
   {
      ChooseTagsDialogFragment.show( this, tags );
   }
   
   
   
   private List< Task > getSelectedTasks()
   {
      return new ArrayList< Task >( ( (AbstractTasksListFragmentAdapter) getTasksListFragment().getListView()
                                                                                               .getAdapter() ).getSelectedItems() );
   }
   
   
   
   private void clearListChoices()
   {
      getTasksListFragment().getListView().clearChoices();
   }
   
   
   
   private void finishActiveActionMode()
   {
      if ( activeActionMode != null )
      {
         activeActionMode.finish();
      }
   }
}
