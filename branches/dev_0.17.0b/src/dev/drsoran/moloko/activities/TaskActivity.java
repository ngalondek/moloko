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

import java.util.ArrayList;
import java.util.Collection;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.mdt.rtm.data.RtmAuth.Perms;
import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ActionBarTabsAdapter;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.NotesListFragment;
import dev.drsoran.moloko.fragments.TaskFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.LocationChooserDialogFragment;
import dev.drsoran.moloko.fragments.listeners.INotesListsFragmentListener;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.rtm.Task;


public class TaskActivity extends MolokoEditFragmentActivity implements
         ITaskFragmentListener, INotesListsFragmentListener

{
   private final static String TASK_TO_DELETE = "task_to_delete";
   
   private final static String NOTES_TO_DELETE = "notes_to_delete";
   
   private final static String TABS_ADAPTER_STATE = "tabs_adapter_state";
   
   public final static int TASK_TAB_POSITION = 0;
   
   public final static int NOTES_TAB_POSITION = 1;
   
   @InstanceState( key = TASK_TO_DELETE, defaultValue = InstanceState.NULL )
   private Task taskToDelete;
   
   @InstanceState( key = NOTES_TO_DELETE, defaultValue = InstanceState.NULL )
   private ArrayList< RtmTaskNote > notesToDelete;
   
   private ActionBarTabsAdapter tabsAdapter;
   
   private TaskFragment taskFragment;
   
   private NotesListFragment notesListFragment;
   
   
   
   public TaskActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_activity );
      createTabs();
   }
   
   
   
   @Override
   public void onAttachFragment( Fragment fragment )
   {
      super.onAttachFragment( fragment );
      
      if ( fragment instanceof TaskFragment )
         taskFragment = (TaskFragment) fragment;
      else if ( fragment instanceof NotesListFragment )
         notesListFragment = (NotesListFragment) fragment;
   }
   
   
   
   public ActionBarTabsAdapter getTabsAdapter()
   {
      return tabsAdapter;
   }
   
   
   
   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      saveTabsAdapterState( outState );
      super.onSaveInstanceState( outState );
   }
   
   
   
   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      restoreTabsAdapterState( state );
   }
   
   
   
   private void createTabs()
   {
      createTabsAdapter();
      
      final String taskId = getTaskIdFromIntent();
      
      createTab( R.string.taskactivity_tab_task,
                 TaskFragment.class,
                 createTaskFragmentConfiguration( taskId ) );
      
      createTab( R.string.taskactivity_tab_notes,
                 NotesListFragment.class,
                 createNotesListFragmentConfiguration( taskId ) );
   }
   
   
   
   private void createTabsAdapter()
   {
      tabsAdapter = new ActionBarTabsAdapter( this,
                                              (ViewPager) findViewById( R.id.pager ) );
   }
   
   
   
   private void createTab( int captionResId,
                           Class< ? extends Fragment > fragmentClass,
                           Bundle config )
   {
      final ActionBar actionBar = getSupportActionBar();
      final Tab tab = actionBar.newTab();
      tab.setText( captionResId );
      
      tabsAdapter.addTab( tab, fragmentClass, config );
   }
   
   
   
   private void saveTabsAdapterState( Bundle outState )
   {
      outState.putInt( TABS_ADAPTER_STATE,
                       getSupportActionBar().getSelectedNavigationIndex() );
   }
   
   
   
   private void restoreTabsAdapterState( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
      {
         getSupportActionBar().setSelectedNavigationItem( savedInstanceState.getInt( TABS_ADAPTER_STATE,
                                                                                     TASK_TAB_POSITION ) );
      }
   }
   
   
   
   public String getTaskIdFromIntent()
   {
      String taskId = null;
      
      final Uri taskUri = getIntent().getData();
      
      if ( taskUri != null )
         taskId = taskUri.getLastPathSegment();
      
      return taskId;
   }
   
   
   
   @Override
   protected void onReEvaluateRtmAccessLevel( Perms currentAccessLevel )
   {
      super.onReEvaluateRtmAccessLevel( currentAccessLevel );
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public void onCompleteTask( Task task )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.setTaskCompletion( this,
                                                                              task,
                                                                              true );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onIncompleteTask( Task task )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.setTaskCompletion( this,
                                                                              task,
                                                                              false );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onPostponeTask( Task task )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.postponeTask( this,
                                                                         task );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onDeleteTask( Task task )
   {
      taskToDelete = task;
      
      final String message = getResources().getQuantityString( R.plurals.tasks_delete,
                                                               1,
                                                               1 );
      new AlertDialogFragment.Builder( R.id.dlg_delete_element ).setMessage( message )
                                                                .setPositiveButton( R.string.btn_delete )
                                                                .setNegativeButton( R.string.btn_cancel )
                                                                .show( this );
   }
   
   
   
   private void deleteTaskImpl()
   {
      final ApplyChangesInfo modifications = TaskEditUtils.deleteTask( TaskActivity.this,
                                                                       taskToDelete );
      if ( applyModifications( modifications ) )
      {
         finish();
      }
   }
   
   
   
   @Override
   public void onEditTask( Task task )
   {
      startActivity( Intents.createEditTaskIntent( this, task ) );
   }
   
   
   
   @Override
   public void onOpenLocation( Task task )
   {
      LocationChooserDialogFragment.show( this, task );
   }
   
   
   
   @Override
   public void onOpenContact( String fullname, String username )
   {
      final Intent intent = Intents.createOpenContactIntent( this,
                                                             fullname,
                                                             username );
      
      // It is possible that we came here from the ContactsListActivity
      // by clicking a contact, clicking a task, clicking the contact again.
      // So we reorder the former contact's tasks list to front.
      intent.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
      
      startActivity( intent );
   }
   
   
   
   @Override
   public void onOpenNote( RtmTaskNote note, int position )
   {
      if ( isWritableAccess() )
      {
         startActivity( Intents.createEditNoteIntent( this, note ) );
      }
   }
   
   
   
   @Override
   public void onAddNote()
   {
      startActivity( Intents.createAddNoteIntent( this,
                                                  taskFragment.getTaskAssertNotNull(),
                                                  Strings.EMPTY_STRING,
                                                  Strings.EMPTY_STRING ) );
   }
   
   
   
   @Override
   public void onDeleteNotes( Collection< RtmTaskNote > notes )
   {
      notesToDelete = new ArrayList< RtmTaskNote >( notes );
      
      final String message = getResources().getQuantityString( R.plurals.notes_delete,
                                                               notes.size(),
                                                               notes.size() );
      new AlertDialogFragment.Builder( R.id.dlg_delete_notes ).setMessage( message )
                                                              .setPositiveButton( R.string.btn_delete )
                                                              .setNegativeButton( R.string.btn_cancel )
                                                              .show( this );
   }
   
   
   
   private void deleteNotesImpl()
   {
      final ApplyChangesInfo modifications = NoteEditUtils.deleteNotes( this,
                                                                        notesToDelete );
      applyModifications( modifications );
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         switch ( dialogId )
         {
            case R.id.dlg_delete_element:
               deleteTaskImpl();
               taskToDelete = null;
               break;
            
            case R.id.dlg_delete_notes:
               deleteNotesImpl();
               notesToDelete = null;
               break;
            
            default :
               super.onAlertDialogFragmentClick( dialogId, tag, which );
         }
      }
      else
      {
         super.onAlertDialogFragmentClick( dialogId, tag, which );
      }
   }
   
   
   
   private Bundle createTaskFragmentConfiguration( String taskId )
   {
      final Bundle config = getFragmentConfigurations( R.id.frag_task );
      
      config.putString( TaskFragment.Config.TASK_ID, taskId );
      
      return config;
   }
   
   
   
   private Bundle createNotesListFragmentConfiguration( String taskId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NotesListFragment.Config.TASK_ID, taskId );
      
      return config;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { taskFragment != null ? taskFragment.getId() : -1,
       notesListFragment != null ? notesListFragment.getId() : -1 };
   }
}
