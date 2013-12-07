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

package dev.drsoran.moloko.app.task;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.Strings;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoEditFragmentActivity;
import dev.drsoran.moloko.app.taskedit.TaskEditActivity;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.adapters.ActionBarViewPagerTabsAdapter;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;


public class TaskActivity extends MolokoEditFragmentActivity implements
         ITaskFragmentListener, INotesListsFragmentListener

{
   private final static String TASK_TO_DELETE = "task_to_delete";
   
   private final static String NOTES_TO_DELETE = "notes_to_delete";
   
   private final static String TABS_ADAPTER_STATE = "tabs_adapter_state";
   
   public final static int TASK_TAB_POSITION = 0;
   
   public final static int NOTES_TAB_POSITION = 1;
   
   private boolean enableAbsViewPagerWorkaround;
   
   @InstanceState( key = TASK_TO_DELETE, defaultValue = InstanceState.NULL )
   private Task taskToDelete;
   
   @InstanceState( key = NOTES_TO_DELETE, defaultValue = InstanceState.NULL )
   private ArrayList< Note > notesToDelete;
   
   private ActionBarViewPagerTabsAdapter tabsAdapter;
   
   private TaskFragment taskFragment;
   
   private NotesListFragment notesListFragment;
   
   
   
   public TaskActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      enableAbsViewPagerWorkaround = getResources().getBoolean( R.bool.env_enable_abs_viewpager_workaround );
      
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
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( enableAbsViewPagerWorkaround )
      {
         return getSelectedViewPagerSupport().onCreateOptionsMenuViewPagerSupportWorkaround( menu,
                                                                                             getSupportMenuInflater() );
      }
      else
      {
         return super.onActivityCreateOptionsMenu( menu );
      }
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      if ( enableAbsViewPagerWorkaround )
      {
         return getSelectedViewPagerSupport().onPrepareOptionsMenuViewPagerSupportWorkaround( menu );
      }
      else
      {
         return super.onPrepareOptionsMenu( menu );
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      if ( enableAbsViewPagerWorkaround )
      {
         return getSelectedViewPagerSupport().onOptionsItemSelectedViewPagerSupportWorkaround( item );
      }
      else
      {
         return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      if ( requestCode == TaskEditActivity.REQ_DEFAULT )
      {
         // The task in edit was deleted by a background sync. Close this
         // activity.
         if ( resultCode == TaskEditActivity.RESULT_DELETED )
         {
            finish();
         }
      }
      else
      {
         super.onActivityResult( requestCode, resultCode, data );
      }
   }
   
   
   
   private void createTabs()
   {
      createTabsAdapter();
      createAbsTabsPagerHandler();
      
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
      tabsAdapter = new ActionBarViewPagerTabsAdapter( this,
                                                       (ViewPager) findViewById( R.id.pager ) );
   }
   
   
   
   private void createAbsTabsPagerHandler()
   {
      if ( enableAbsViewPagerWorkaround )
      {
         new AbsWorkaroundPagerHander( (ViewPager) findViewById( R.id.pager ) );
      }
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
         final int savedTabPosition = savedInstanceState.getInt( TABS_ADAPTER_STATE,
                                                                 TASK_TAB_POSITION );
         final ActionBar actionBar = getSupportActionBar();
         
         if ( actionBar.getSelectedNavigationIndex() != savedTabPosition )
         {
            actionBar.setSelectedNavigationItem( savedTabPosition );
         }
      }
   }
   
   
   
   private IAbsViewPagerSupport getSelectedViewPagerSupport()
   {
      switch ( getSupportActionBar().getSelectedNavigationIndex() )
      {
         case TASK_TAB_POSITION:
            return taskFragment;
         case NOTES_TAB_POSITION:
            return notesListFragment;
         default :
            throw new IllegalStateException();
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
   public void onCompleteTask( Task task )
   {
      getAppContext().getContentEditService()
                     .completeTask( task, System.currentTimeMillis() );
   }
   
   
   
   @Override
   public void onIncompleteTask( Task task )
   {
      getAppContext().getContentEditService().incompleteTask( task );
   }
   
   
   
   @Override
   public void onPostponeTask( Task task )
   {
      getAppContext().getContentEditService().postponeTask( task );
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
      getAppContext().getContentEditService().deleteTask( taskToDelete );
   }
   
   
   
   @Override
   public void onEditTask( Task task )
   {
      startActivityForResult( Intents.createEditTaskIntent( this, task ),
                              TaskEditActivity.REQ_DEFAULT );
   }
   
   
   
   @Override
   public void onOpenLocation( Task task )
   {
      startActivity( Intents.createOpenLocationWithOtherAppChooser( task.getLongitude(),
                                                                    task.getLatitude(),
                                                                    task.getZoom() ) );
   }
   
   
   
   @Override
   public void onOpenContact( String fullname, String username )
   {
      final Intent intent = Intents.createOpenContactIntent( this,
                                                             fullname,
                                                             username );
      startActivity( intent );
   }
   
   
   
   @Override
   public void onOpenNote( Note note, int position )
   {
      if ( isWritableAccess() )
      {
         startActivity( Intents.createEditNoteIntent( this,
                                                      taskFragment.getTaskAssertNotNull(),
                                                      note ) );
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
   public void onDeleteNotes( Collection< Note > notes )
   {
      notesToDelete = new ArrayList< Note >( notes );
      
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
      final Task task = taskFragment.getTaskAssertNotNull();
      for ( Note note : notesToDelete )
      {
         task.removeNote( note );
      }
      
      getAppContext().getContentEditService().updateTask( task );
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
      
      config.putString( Intents.Extras.KEY_TASK_ID, taskId );
      
      return config;
   }
   
   
   
   private Bundle createNotesListFragmentConfiguration( String taskId )
   {
      final Bundle config = new Bundle();
      
      config.putString( Intents.Extras.KEY_TASK_ID, taskId );
      
      return config;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { taskFragment != null ? taskFragment.getId() : -1,
       notesListFragment != null ? notesListFragment.getId() : -1 };
   }
   
   
   private class AbsWorkaroundPagerHander implements OnPageChangeListener
   {
      public AbsWorkaroundPagerHander( ViewPager pager )
      {
         pager.setOnPageChangeListener( this );
      }
      
      
      
      @Override
      public void onPageScrollStateChanged( int state )
      {
         tabsAdapter.onPageScrollStateChanged( state );
      }
      
      
      
      @Override
      public void onPageScrolled( int position,
                                  float positionOffset,
                                  int positionOffsetPixels )
      {
         tabsAdapter.onPageScrolled( position,
                                     positionOffset,
                                     positionOffsetPixels );
      }
      
      
      
      @Override
      public void onPageSelected( int position )
      {
         // Note: We first need to update the new selected
         // page and then invalidate the menu because the
         // menu invalidation runs synchronous.
         tabsAdapter.onPageSelected( position );
         supportInvalidateOptionsMenu();
      }
   }
}
