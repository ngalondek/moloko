/*
 * Copyright (c) 2010 Ronny R�hricht
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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.dialogs.LocationChooser;
import dev.drsoran.moloko.fragments.NoteAddFragment;
import dev.drsoran.moloko.fragments.NoteFragment;
import dev.drsoran.moloko.fragments.TaskFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.loaders.TaskLoader;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class TaskActivity extends MolokoFragmentActivity implements
         ITaskFragmentListener, LoaderCallbacks< Task >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskActivity.class.getSimpleName();
   
   
   public static class Config
   {
      public final static String TASK = "task";
      
      private final static String TASK_ID = "task_id";
      
      private final static String EDIT_MODE_FRAG_ID = "editModeFragmentId";
   }
   
   
   protected static class OptionsMenu
   {
      public final static int POSTPONE_TASK = R.id.menu_postpone_selected_tasks;
      
      public final static int COMPLETE_TASK = R.id.menu_complete_selected_tasks;
      
      public final static int UNCOMPLETE_TASK = R.id.menu_uncomplete_selected_tasks;
      
      public final static int DELETE_TASK = R.id.menu_delete_selected_tasks;
      
      public final static int SAVE = R.id.menu_save;
      
      public final static int ABORT = R.id.menu_abort_edit;
   }
   
   private final static int TASK_LOADER_ID = 1;
   
   private final static String FRAGMENT_LAYOUT_TAG_STUB = "frag_layout_";
   
   private final static int NEW_NOTE_TEMPORARY_CONTAINER_ID = 1;
   
   private final static String NEW_NOTE_TEMPORARY_ID = Integer.toString( NEW_NOTE_TEMPORARY_CONTAINER_ID );
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      // LoaderManager.enableDebugLogging( true );
      // FragmentManager.enableDebugLogging( true );
      
      setContentView( R.layout.task_activity );
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_VIEW ) )
      {
         if ( getConfiguredTask() != null )
            showContent();
         else
            reloadTask();
      }
   }
   
   
   
   private void reloadTask()
   {
      final Uri taskUri = getIntent().getData();
      
      if ( taskUri != null )
      {
         final String taskId = taskUri.getLastPathSegment();
         final Bundle loaderConfig = new Bundle();
         loaderConfig.putString( Config.TASK_ID, taskId );
         
         getSupportLoaderManager().restartLoader( TASK_LOADER_ID,
                                                  loaderConfig,
                                                  this );
      }
   }
   
   
   
   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      if ( config.containsKey( Config.TASK ) )
         configuration.putParcelable( Config.TASK,
                                      config.getParcelable( Config.TASK ) );
      
      if ( config.containsKey( Config.EDIT_MODE_FRAG_ID ) )
         configuration.putInt( Config.EDIT_MODE_FRAG_ID,
                               config.getInt( Config.EDIT_MODE_FRAG_ID ) );
   }
   
   
   
   @Override
   public void putDefaultConfigurationTo( Bundle bundle )
   {
   }
   
   
   
   public Task getConfiguredTask()
   {
      return configuration.getParcelable( Config.TASK );
   }
   
   
   
   public Task getConfiguredTaskAssertNotNull()
   {
      final Task task = getConfiguredTask();
      
      if ( task == null )
         throw new IllegalStateException( "task must not be null" );
      
      return task;
   }
   
   
   
   public int getConfiguredEditModeFragmentId()
   {
      return configuration.getInt( Config.EDIT_MODE_FRAG_ID, 0 );
   }
   
   
   
   public void setConfiguredEditModeFragmentId( int fragmentId )
   {
      configuration.putInt( Config.EDIT_MODE_FRAG_ID, fragmentId );
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final Task task = getConfiguredTask();
      
      if ( task != null )
      {
         final boolean hasRtmWriteAccess = AccountUtils.isWriteableAccess( this );
         final boolean isInEditMode = IsActivityInEditMode();
         
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.COMPLETE_TASK,
                                      getString( R.string.app_task_complete ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_menu_complete,
                                      MenuItem.SHOW_AS_ACTION_ALWAYS,
                                      !isInEditMode && hasRtmWriteAccess
                                         && task.getCompleted() == null );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.UNCOMPLETE_TASK,
                                      getString( R.string.app_task_uncomplete ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_menu_incomplete,
                                      MenuItem.SHOW_AS_ACTION_ALWAYS,
                                      !isInEditMode && hasRtmWriteAccess
                                         && task.getCompleted() != null );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.POSTPONE_TASK,
                                      getString( R.string.app_task_postpone ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_menu_postponed,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      !isInEditMode && hasRtmWriteAccess );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.DELETE_TASK,
                                      getString( R.string.app_task_delete ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_menu_trash,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      !isInEditMode && hasRtmWriteAccess );
         
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.SAVE,
                                      getString( R.string.app_save ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_menu_disc,
                                      MenuItem.SHOW_AS_ACTION_ALWAYS,
                                      isInEditMode && hasRtmWriteAccess );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.ABORT,
                                      getString( R.string.phr_cancel_sync ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_menu_cancel,
                                      MenuItem.SHOW_AS_ACTION_ALWAYS,
                                      isInEditMode && hasRtmWriteAccess );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.COMPLETE_TASK:
            return true;
            
         case OptionsMenu.UNCOMPLETE_TASK:
            return true;
            
         case OptionsMenu.POSTPONE_TASK:
            return true;
            
         case OptionsMenu.DELETE_TASK:
            return true;
            
         case OptionsMenu.SAVE:
            finishEditing( false );
            return true;
            
         case OptionsMenu.ABORT:
            finishEditing( true );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   protected void initContent( ViewGroup container )
   {
      super.initContent( container );
      
      final Task task = getConfiguredTaskAssertNotNull();
      
      initTaskFragmentWithTask( task );
      createOrUpdateNoteFragmentsFromTask( task );
      
      onReEvaluateRtmAccessLevel( AccountUtils.getAccessLevel( this ) );
      
      if ( IsActivityInAddingNewNoteMode()
         && !existsFragmentContainer( NEW_NOTE_TEMPORARY_ID ) )
         createAddNewNoteFragmentContainer();
      
      setActivityInEditMode( getConfiguredEditModeFragmentId() );
   }
   
   
   
   public void onEditTask( View taskEditButton )
   {
      
   }
   
   
   
   public void onAddNote( View addNoteButton )
   {
      addNoteImpl();
   }
   
   
   
   public void onEditNote( View noteEditButton )
   {
      final Fragment fragment = findAddedFragmentByTag( (String) noteEditButton.getTag() );
      
      if ( setFragmentInEditMode( fragment ) )
         setActivityInEditMode( fragment.getId() );
   }
   
   
   
   public void onDeleteNote( View noteDeleteButton )
   {
      final String noteId = (String) noteDeleteButton.getTag();
      
      UIUtils.newDeleteElementDialog( this,
                                      getString( R.string.app_note ),
                                      new Runnable()
                                      {
                                         @Override
                                         public void run()
                                         {
                                            deleteNoteImpl( noteId );
                                         }
                                      },
                                      null ).show();
   }
   
   
   
   @Override
   protected void onReEvaluateRtmAccessLevel( Perms currentAccessLevel )
   {
      super.onReEvaluateRtmAccessLevel( currentAccessLevel );
      
      if ( !IsActivityInEditMode() )
      {
         showEditButtons( currentAccessLevel.allowsEditing() );
         invalidateOptionsMenu();
      }
      else if ( IsActivityInEditMode() && !currentAccessLevel.allowsEditing() )
      {
         // TODO: In this case we have to force cancel editing, Show message box with reason.
         finishEditing( true );
      }
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( IsActivityInEditMode() )
         finishEditing( true );
      else
         super.onBackPressed();
   }
   
   
   
   @Override
   protected boolean onFinishActivityByHome()
   {
      boolean finish = super.onFinishActivityByHome();
      
      if ( finish && IsActivityInEditMode() )
         finish = finishEditingAndFinishActivity( true );
      
      return finish;
   }
   
   
   
   public void onEditTask( String taskId )
   {
      startActivityForResult( Intents.createEditTaskIntent( this, taskId ),
                              TaskEditActivity.REQ_EDIT_TASK );
   }
   
   
   
   public void onDeleteTask( String taskId )
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      UIUtils.newDeleteElementDialog( this, task.getName(), new Runnable()
      {
         @Override
         public void run()
         {
            TaskEditUtils.deleteTask( TaskActivity.this, task );
            finish();
         }
      }, null ).show();
   }
   
   
   
   public void onPostponeTask( String taskId )
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      TaskEditUtils.postponeTask( this, task );
   }
   
   
   
   public void onCompleteTask( String taskId )
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      TaskEditUtils.setTaskCompletion( this, task, true );
   }
   
   
   
   public void onUncompleteTask( String taskId )
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      TaskEditUtils.setTaskCompletion( this, task, false );
   }
   
   
   
   @Override
   public void onOpenLocation( String locationId )
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      new LocationChooser( this, task ).showChooser();
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
   
   
   
   private void addNoteImpl()
   {
      setActivityInEditMode( createAddNewNoteFragment() );
   }
   
   
   
   private void deleteNoteImpl( String noteId )
   {
      if ( NoteEditUtils.deleteNote( this, noteId ) )
      {
         removeNoteFragmentById( noteId );
      }
   }
   
   
   
   private void setActivityInEditMode( int editFragmentId )
   {
      setConfiguredEditModeFragmentId( editFragmentId );
      
      showEditButtons( editFragmentId != 0 ? false : true );
      invalidateOptionsMenu();
   }
   
   
   
   private boolean IsActivityInEditMode()
   {
      return getConfiguredEditModeFragmentId() != 0;
   }
   
   
   
   private boolean IsActivityInAddingNewNoteMode()
   {
      return getConfiguredEditModeFragmentId() == NEW_NOTE_TEMPORARY_CONTAINER_ID;
   }
   
   
   
   private boolean finishEditing( boolean canceled )
   {
      if ( !IsActivityInEditMode() )
         throw new IllegalStateException( "expected to be in edit mode" );
      
      if ( canceled
         && isEditFragmentModified( getConfiguredEditModeFragmentId() ) )
      {
         finishEditingShowCancelDialog( new Runnable()
         {
            @Override
            public void run()
            {
               setActivityInEditMode( 0 );
            }
         } );
         
         return false;
      }
      else
      {
         if ( finishFragmentEditing( getConfiguredEditModeFragmentId(),
                                     canceled ) )
            setActivityInEditMode( 0 );
         
         return true;
      }
   }
   
   
   
   private boolean finishEditingAndFinishActivity( boolean canceled )
   {
      if ( !IsActivityInEditMode() )
         throw new IllegalStateException( "expected to be in edit mode" );
      
      if ( canceled
         && isEditFragmentModified( getConfiguredEditModeFragmentId() ) )
      {
         finishEditingShowCancelDialog( new Runnable()
         {
            @Override
            public void run()
            {
               finish();
            }
         } );
         
         return false;
      }
      else
      {
         // Do not call finish() here since the caller will finish if we return
         // true
         return finishFragmentEditing( getConfiguredEditModeFragmentId(),
                                       canceled );
      }
   }
   
   
   
   private void finishEditingShowCancelDialog( final Runnable cancelAction )
   {
      UIUtils.newCancelWithChangesDialog( this, new Runnable()
      {
         @Override
         public void run()
         {
            finishFragmentEditing( getConfiguredEditModeFragmentId(), true );
            if ( cancelAction != null )
               cancelAction.run();
         }
      }, null ).show();
   }
   
   
   
   private boolean setFragmentInEditMode( Fragment fragment )
   {
      if ( fragment instanceof IEditableFragment )
      {
         final IEditableFragment< ? > editableFragment = (IEditableFragment< ? >) fragment;
         
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         transaction.replace( fragment.getId(),
                              (Fragment) editableFragment.createEditFragmentInstance(),
                              fragment.getTag() );
         transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
         
         transaction.commit();
         
         return true;
      }
      
      return false;
   }
   
   
   
   private boolean isFragmentInEditMode( int fragmentId )
   {
      return ( getConfiguredEditModeFragmentId() == fragmentId );
   }
   
   
   
   private boolean isEditFragmentModified( int fragmentId )
   {
      boolean hasChanges = false;
      
      final Fragment fragment = findAddedFragmentById( fragmentId );
      
      if ( fragment instanceof IEditFragment )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         hasChanges = editFragment.hasChanges();
      }
      
      return hasChanges;
   }
   
   
   
   private boolean finishFragmentEditing( int fragmentContainerId,
                                          boolean canceled )
   {
      if ( IsActivityInAddingNewNoteMode() )
         return finishAddingNewNote( canceled );
      else
         return finishFragmentEditingImpl( fragmentContainerId, canceled );
   }
   
   
   
   private boolean finishFragmentEditingImpl( int fragmentContainerId,
                                              boolean canceled )
   {
      boolean finished = true;
      
      final Fragment fragment = findAddedFragmentById( fragmentContainerId );
      
      if ( fragment instanceof IEditFragment )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         
         if ( !canceled )
            finished = editFragment.onFinishEditing();
         else
            editFragment.onCancelEditing();
         
         if ( finished )
         {
            final Fragment editableFragment = (Fragment) editFragment.createEditableFragmentInstance();
            
            if ( editableFragment != null )
            {
               final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               
               transaction.replace( fragment.getId(),
                                    editableFragment,
                                    fragment.getTag() );
               transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
               
               transaction.commit();
            }
            else
            {
               removeFragmentByTag( fragment.getTag() );
            }
         }
      }
      
      return finished;
   }
   
   
   
   private ViewGroup getFragmentContainer()
   {
      return (ViewGroup) findViewById( R.id.fragment_container );
   }
   
   
   
   private boolean existsFragmentContainer( int fragmentContainerId )
   {
      return getFragmentContainer().findViewById( fragmentContainerId ) != null;
   }
   
   
   
   private boolean existsFragmentContainer( String fragmentTag )
   {
      return getFragmentContainer().findViewWithTag( createFragmentLayoutTag( fragmentTag ) ) != null;
   }
   
   
   
   private void removeFragmentByTag( String fragmentTag )
   {
      final Fragment fragment = findAddedFragmentByTag( fragmentTag );
      
      if ( fragment != null )
      {
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         transaction.remove( fragment );
         transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
         
         transaction.commit();
         
         final ViewGroup fragmentContainer = getFragmentContainer();
         final View taggedFragmentContainer = fragmentContainer.findViewWithTag( createFragmentLayoutTag( fragmentTag ) );
         
         if ( taggedFragmentContainer != null )
            fragmentContainer.removeView( taggedFragmentContainer );
      }
   }
   
   
   
   private void showEditButtons( boolean show )
   {
      findViewById( R.id.task_buttons ).setVisibility( show ? View.VISIBLE
                                                           : View.GONE );
      
      final ViewGroup fragmentContainer = getFragmentContainer();
      
      for ( int i = 0, cnt = fragmentContainer.getChildCount(); i < cnt; ++i )
      {
         final View view = fragmentContainer.getChildAt( i );
         final Object tag = view.getTag();
         
         if ( tag instanceof String
            && ( (String) tag ).startsWith( FRAGMENT_LAYOUT_TAG_STUB ) )
         {
            final View buttonsContainer = view.findViewById( R.id.note_buttons );
            if ( buttonsContainer != null )
               buttonsContainer.setVisibility( show ? View.VISIBLE : View.GONE );
         }
      }
   }
   
   
   
   private void initTaskFragmentWithTask( Task task )
   {
      final Fragment fragment = TaskFragment.newInstance( createTaskFragmentConfiguration( task ) );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      if ( findAddedFragmentById( R.id.frag_task ) == null )
         transaction.add( R.id.frag_task, fragment );
      else
         transaction.replace( R.id.frag_task, fragment );
      
      transaction.commitAllowingStateLoss();
   }
   
   
   
   private Bundle createTaskFragmentConfiguration( Task task )
   {
      final Bundle config = getFragmentConfigurations( R.id.frag_task );
      
      config.putParcelable( TaskFragment.Config.TASK, task );
      
      return config;
   }
   
   
   
   private void createOrUpdateNoteFragmentsFromTask( Task task )
   {
      if ( task.getNumberOfNotes() > 0 )
      {
         final ViewGroup fragmentContainer = getFragmentContainer();
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         final List< String > noteIds = task.getNoteIds();
         
         for ( int i = 0, cnt = noteIds.size(); i < cnt; ++i )
         {
            final String noteId = noteIds.get( i );
            
            final int noteFragmentContainerId = Integer.parseInt( noteId );
            final Fragment noteFragment = findAddedFragmentById( noteFragmentContainerId );
            
            if ( noteFragment != null )
            {
               // If we have a fragment but no container, then the activity was destroyed but
               // the fragments have been saved. E.g. orientation change. In this case we do not
               // touch the fragment but have to recreate the container.
               if ( !existsFragmentContainer( noteFragmentContainerId ) )
                  createAndAddNoteFragmentContainer( fragmentContainer,
                                                     noteFragmentContainerId,
                                                     noteId );
               
               else
                  // If we have a fragment and a container, then the loader has notified a change
                  // and reloaded the task.
                  updateNoteFragment( fragmentContainer,
                                      noteFragment,
                                      transaction );
            }
            else
            {
               createNoteFragmentFromId( fragmentContainer,
                                         noteFragmentContainerId,
                                         noteId,
                                         transaction );
            }
         }
         
         transaction.commitAllowingStateLoss();
      }
   }
   
   
   
   private void createNoteFragmentFromId( ViewGroup fragmentContainer,
                                          int fragmentContainerId,
                                          String noteId,
                                          FragmentTransaction transaction )
   {
      createAndAddNoteFragmentContainer( fragmentContainer,
                                         fragmentContainerId,
                                         noteId );
      
      final Fragment fragment = NoteFragment.newInstance( createNoteFragmentConfiguration( noteId ) );
      transaction.add( fragmentContainerId, fragment, noteId );
   }
   
   
   
   private void updateNoteFragment( ViewGroup fragmentContainer,
                                    Fragment noteFragment,
                                    FragmentTransaction transaction )
   {
      final int fragmentId = noteFragment.getId();
      
      if ( !isFragmentInEditMode( fragmentId ) )
      {
         final String noteId = noteFragment.getTag();
         
         final Fragment fragment = NoteFragment.newInstance( createNoteFragmentConfiguration( noteId ) );
         transaction.replace( fragmentId, fragment, noteId );
      }
   }
   
   
   
   private int createAddNewNoteFragment()
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      final int noteFragmentContainerId = createAddNewNoteFragmentContainer();
      
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      final Fragment fragment = NoteAddFragment.newInstance( createAddNewNoteFragmentConfiguration( task.getTaskSeriesId() ) );
      
      transaction.add( noteFragmentContainerId, fragment, NEW_NOTE_TEMPORARY_ID );
      transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
      
      transaction.commit();
      
      return noteFragmentContainerId;
   }
   
   
   
   private int createAddNewNoteFragmentContainer()
   {
      final ViewGroup fragmentContainer = getFragmentContainer();
      
      createAndAddNoteFragmentContainer( fragmentContainer,
                                         NEW_NOTE_TEMPORARY_CONTAINER_ID,
                                         NEW_NOTE_TEMPORARY_ID );
      
      return NEW_NOTE_TEMPORARY_CONTAINER_ID;
   }
   
   
   
   private Bundle createAddNewNoteFragmentConfiguration( String taskSeriesId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteAddFragment.Config.TASKSERIES_ID, taskSeriesId );
      
      return config;
   }
   
   
   
   private boolean finishAddingNewNote( boolean canceled )
   {
      final IEditFragment< ? > addNewNoteFragment = (IEditFragment< ? >) findAddedFragmentByTag( NEW_NOTE_TEMPORARY_ID );
      
      boolean ok = true;
      
      if ( !canceled )
         ok = addNewNoteFragment.onFinishEditing();
      else
         addNewNoteFragment.onCancelEditing();
      
      if ( ok )
      {
         removeNoteFragmentById( NEW_NOTE_TEMPORARY_ID );
      }
      
      return ok;
   }
   
   
   
   private void removeNoteFragmentById( String noteId )
   {
      removeFragmentByTag( noteId );
   }
   
   
   
   private View createAndAddNoteFragmentContainer( ViewGroup fragmentContainer,
                                                   int fragmentContainerId,
                                                   String noteId )
   {
      final View taskNote = getLayoutInflater().inflate( R.layout.task_note,
                                                         fragmentContainer,
                                                         false );
      taskNote.setTag( createFragmentLayoutTag( noteId ) );
      
      final View noteFragContainer = taskNote.findViewById( R.id.note_fragment_container );
      noteFragContainer.setId( fragmentContainerId );
      noteFragContainer.setTag( noteId );
      
      final View editNoteButton = taskNote.findViewById( R.id.note_buttons_edit );
      editNoteButton.setTag( noteId );
      
      final View deleteNoteButton = taskNote.findViewById( R.id.note_buttons_delete );
      deleteNoteButton.setTag( noteId );
      
      fragmentContainer.addView( taskNote );
      
      return noteFragContainer;
   }
   
   
   
   private String createFragmentLayoutTag( String fragmentTag )
   {
      return FRAGMENT_LAYOUT_TAG_STUB + fragmentTag;
   }
   
   
   
   private Bundle createNoteFragmentConfiguration( String noteId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteFragment.Config.NOTE_ID, noteId );
      
      return config;
   }
   
   
   
   private Fragment findAddedFragmentById( int fragmentId )
   {
      Fragment fragment = getSupportFragmentManager().findFragmentById( fragmentId );
      
      if ( fragment != null && ( !fragment.isAdded() || fragment.isDetached() ) )
         fragment = null;
      
      return fragment;
   }
   
   
   
   private Fragment findAddedFragmentByTag( String fragentTag )
   {
      Fragment fragment = getSupportFragmentManager().findFragmentByTag( fragentTag );
      
      if ( fragment != null && !fragment.isAdded() )
         fragment = null;
      
      return fragment;
   }
   
   
   
   @Override
   public Loader< Task > onCreateLoader( int id, Bundle args )
   {
      showLoadingSpinner();
      
      return new TaskLoader( this, args.getString( Config.TASK_ID ) );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< Task > loader, Task data )
   {
      if ( data == null )
         showElementNotFoundError( getResources().getQuantityString( R.plurals.g_task,
                                                                     1 ) );
      else
      {
         final Bundle newConfig = getConfiguration();
         newConfig.putParcelable( Config.TASK, data );
         configure( newConfig );
         
         showContent();
      }
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< Task > loader )
   {
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
}
