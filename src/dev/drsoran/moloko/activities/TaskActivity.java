/*
 * Copyright (c) 2010 Ronny Röhricht
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
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.dialogs.LocationChooser;
import dev.drsoran.moloko.fragments.NoteAddFragment;
import dev.drsoran.moloko.fragments.NoteEditFragment;
import dev.drsoran.moloko.fragments.NoteFragment;
import dev.drsoran.moloko.fragments.TaskFragment;
import dev.drsoran.moloko.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class TaskActivity extends MolokoFragmentActivity implements
         ITaskFragmentListener, ILoaderFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskActivity.class.getSimpleName();
   
   
   public static class Config
   {
      private final static String EDIT_MODE_FRAG_ID = "editModeFragmentId";
      
      private final static String NOTE_FRAGMENT_CONTAINERS = "note_fragment_containers";
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
   

   private enum FinishEditMode
   {
      SAVE, CANCELED, FORCE_CANCELED
   }
   

   private final static class NoteFragmentContainerState implements Parcelable
   {
      @SuppressWarnings( "unused" )
      public static final Parcelable.Creator< NoteFragmentContainerState > CREATOR = new Parcelable.Creator< NoteFragmentContainerState >()
      {
         
         @Override
         public NoteFragmentContainerState createFromParcel( Parcel source )
         {
            return new NoteFragmentContainerState( source );
         }
         


         @Override
         public NoteFragmentContainerState[] newArray( int size )
         {
            return new NoteFragmentContainerState[ size ];
         }
         
      };
      
      public final String noteId;
      
      public final int noteFragmentContainerId;
      
      

      public NoteFragmentContainerState( String noteId,
         int noteFragmentContainerId )
      {
         this.noteId = noteId;
         this.noteFragmentContainerId = noteFragmentContainerId;
      }
      


      public NoteFragmentContainerState( Parcel source )
      {
         noteId = source.readString();
         noteFragmentContainerId = source.readInt();
      }
      


      @Override
      public void writeToParcel( Parcel dest, int flags )
      {
         dest.writeString( noteId );
         dest.writeInt( noteFragmentContainerId );
      }
      


      @Override
      public int describeContents()
      {
         return 0;
      }
   }
   
   private final static String TASK_NOTE_LAYOUT_TAG_STUB = "frag_layout_";
   
   private final static int NEW_NOTE_TEMPORARY_CONTAINER_ID = 1;
   
   private final static String NEW_NOTE_TEMPORARY_ID = Integer.toString( NEW_NOTE_TEMPORARY_CONTAINER_ID );
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_activity );
      restoreNoteFragmentContainers();
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_VIEW ) )
      {
         createTaskFragment();
         
         onReEvaluateRtmAccessLevel( AccountUtils.getAccessLevel( this ) );
         
         setActivityInEditMode( getConfiguredEditModeFragmentId() );
      }
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      saveNoteFragmentContainers();
      
      super.onSaveInstanceState( outState );
   }
   


   private void saveNoteFragmentContainers()
   {
      final Task task = getTask();
      
      if ( task != null && task.getNumberOfNotes() > 0 )
      {
         final List< NoteFragmentContainerState > noteFragmentContainers = new ArrayList< NoteFragmentContainerState >( task.getNumberOfNotes() );
         final ViewGroup fragmentContainer = getFragmentContainer();
         final List< String > noteIds = task.getNoteIds();
         
         for ( int i = 0, cnt = noteIds.size(); i < cnt; ++i )
         {
            final String noteId = noteIds.get( i );
            final View noteFragmentContainer = fragmentContainer.findViewWithTag( noteId );
            
            if ( noteFragmentContainer != null )
               noteFragmentContainers.add( new NoteFragmentContainerState( noteId,
                                                                           noteFragmentContainer.getId() ) );
         }
         
         if ( IsActivityInAddingNewNoteMode() )
            noteFragmentContainers.add( new NoteFragmentContainerState( NEW_NOTE_TEMPORARY_ID,
                                                                        NEW_NOTE_TEMPORARY_CONTAINER_ID ) );
         
         setConfiguredNoteFragmentContainers( noteFragmentContainers );
      }
   }
   


   private void restoreNoteFragmentContainers()
   {
      final List< NoteFragmentContainerState > noteFragmentContainers = getConfiguredNoteFragmentContainers();
      
      if ( noteFragmentContainers != null )
      {
         final ViewGroup fragmentContainer = getFragmentContainer();
         
         for ( NoteFragmentContainerState noteFragmentContainer : noteFragmentContainers )
         {
            createAndAddNoteFragmentContainer( fragmentContainer,
                                               noteFragmentContainer.noteFragmentContainerId,
                                               noteFragmentContainer.noteId );
         }
      }
      
      setConfiguredNoteFragmentContainers( null );
   }
   


   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      if ( config.containsKey( Config.EDIT_MODE_FRAG_ID ) )
         configuration.putInt( Config.EDIT_MODE_FRAG_ID,
                               config.getInt( Config.EDIT_MODE_FRAG_ID ) );
      
      if ( config.containsKey( Config.NOTE_FRAGMENT_CONTAINERS ) )
         configuration.putParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS,
                                               config.getParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS ) );
   }
   


   @Override
   public void putDefaultConfigurationTo( Bundle bundle )
   {
   }
   


   public int getConfiguredEditModeFragmentId()
   {
      return configuration.getInt( Config.EDIT_MODE_FRAG_ID, 0 );
   }
   


   public void setConfiguredEditModeFragmentId( int fragmentId )
   {
      configuration.putInt( Config.EDIT_MODE_FRAG_ID, fragmentId );
   }
   


   public List< NoteFragmentContainerState > getConfiguredNoteFragmentContainers()
   {
      return configuration.getParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS );
   }
   


   public void setConfiguredNoteFragmentContainers( List< NoteFragmentContainerState > noteFragmentContainers )
   {
      if ( noteFragmentContainers != null )
         configuration.putParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS,
                                               new ArrayList< NoteFragmentContainerState >( noteFragmentContainers ) );
      else
         configuration.remove( Config.NOTE_FRAGMENT_CONTAINERS );
   }
   


   public String getTaskIdFromIntent()
   {
      String taskId = null;
      
      final Uri taskUri = getIntent().getData();
      
      if ( taskUri != null )
         taskId = taskUri.getLastPathSegment();
      
      return taskId;
   }
   


   public Task getTask()
   {
      final TaskFragment taskFragment = (TaskFragment) findAddedFragmentById( R.id.frag_task );
      
      return taskFragment != null ? taskFragment.getConfiguredTask() : null;
   }
   


   public Task getTaskAssertNotNull()
   {
      final Task task = getTask();
      
      if ( task == null )
         throw new IllegalStateException( "task must not be null" );
      
      return task;
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final Task task = getTask();
      
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
            TaskEditUtils.setTaskCompletion( this, getTaskAssertNotNull(), true );
            return true;
            
         case OptionsMenu.UNCOMPLETE_TASK:
            TaskEditUtils.setTaskCompletion( this,
                                             getTaskAssertNotNull(),
                                             false );
            return true;
            
         case OptionsMenu.POSTPONE_TASK:
            TaskEditUtils.postponeTask( this, getTaskAssertNotNull() );
            return true;
            
         case OptionsMenu.DELETE_TASK:
            UIUtils.newDeleteElementDialog( this,
                                            getString( R.string.app_task ),
                                            new Runnable()
                                            {
                                               @Override
                                               public void run()
                                               {
                                                  TaskEditUtils.deleteTask( TaskActivity.this,
                                                                            getTaskAssertNotNull() );
                                                  finish();
                                               }
                                            },
                                            null )
                   .show();
            return true;
            
         case OptionsMenu.SAVE:
            finishEditing( FinishEditMode.SAVE );
            return true;
            
         case OptionsMenu.ABORT:
            finishEditing( FinishEditMode.CANCELED );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
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
         finishEditing( FinishEditMode.FORCE_CANCELED );
      }
   }
   


   @Override
   public void onBackPressed()
   {
      if ( IsActivityInEditMode() )
         finishEditing( FinishEditMode.CANCELED );
      else
         super.onBackPressed();
   }
   


   @Override
   protected boolean onFinishActivityByHome()
   {
      boolean finish = super.onFinishActivityByHome();
      
      if ( finish && IsActivityInEditMode() )
         finish = finishEditingAndFinishActivity( FinishEditMode.CANCELED );
      
      return finish;
   }
   


   public void onEditTask( String taskId )
   {
      startActivityForResult( Intents.createEditTaskIntent( this, taskId ),
                              TaskEditActivity.REQ_EDIT_TASK );
   }
   


   public void onDeleteTask( String taskId )
   {
      final Task task = getTaskAssertNotNull();
      
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
      final Task task = getTaskAssertNotNull();
      
      TaskEditUtils.postponeTask( this, task );
   }
   


   public void onCompleteTask( String taskId )
   {
      final Task task = getTaskAssertNotNull();
      
      TaskEditUtils.setTaskCompletion( this, task, true );
   }
   


   public void onUncompleteTask( String taskId )
   {
      final Task task = getTaskAssertNotNull();
      
      TaskEditUtils.setTaskCompletion( this, task, false );
   }
   


   @Override
   public void onOpenLocation( String locationId )
   {
      final Task task = getTaskAssertNotNull();
      
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
      setActivityInEditMode( createAddNewNoteFragment( createAddNewNoteFragmentConfiguration( getTaskAssertNotNull().getTaskSeriesId() ) ) );
   }
   


   private void deleteNoteImpl( String noteId )
   {
      if ( NoteEditUtils.deleteNote( this, noteId ) )
      {
         removeNoteFragmentById( noteId,
                                 FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
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
   


   private boolean finishEditing( FinishEditMode how )
   {
      if ( !IsActivityInEditMode() )
         throw new IllegalStateException( "expected to be in edit mode" );
      
      if ( how == FinishEditMode.CANCELED
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
         if ( finishFragmentEditing( getConfiguredEditModeFragmentId(), how ) )
            setActivityInEditMode( 0 );
         
         return true;
      }
   }
   


   private boolean finishEditingAndFinishActivity( FinishEditMode how )
   {
      if ( !IsActivityInEditMode() )
         throw new IllegalStateException( "expected to be in edit mode" );
      
      if ( how == FinishEditMode.CANCELED
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
         return finishFragmentEditing( getConfiguredEditModeFragmentId(), how );
      }
   }
   


   private void finishEditingShowCancelDialog( final Runnable cancelAction )
   {
      UIUtils.newCancelWithChangesDialog( this, new Runnable()
      {
         @Override
         public void run()
         {
            finishFragmentEditing( getConfiguredEditModeFragmentId(),
                                   FinishEditMode.CANCELED );
            if ( cancelAction != null )
               cancelAction.run();
         }
      },
                                          null ).show();
   }
   


   private boolean setFragmentInEditMode( Fragment fragment )
   {
      if ( fragment instanceof IEditableFragment< ? > )
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
   


   private boolean isEditFragmentModified( int fragmentId )
   {
      boolean hasChanges = false;
      
      final Fragment fragment = findAddedFragmentById( fragmentId );
      
      if ( fragment instanceof IEditFragment< ? > )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         hasChanges = editFragment.hasChanges();
      }
      
      return hasChanges;
   }
   


   private boolean finishFragmentEditing( int fragmentContainerId,
                                          FinishEditMode how )
   {
      if ( IsActivityInAddingNewNoteMode() )
         return finishAddingNewNote( how );
      else
         return finishFragmentEditingImpl( fragmentContainerId, how );
   }
   


   private boolean finishFragmentEditingImpl( int fragmentContainerId,
                                              FinishEditMode how )
   {
      boolean finished = true;
      
      final Fragment fragment = findAddedFragmentById( fragmentContainerId );
      
      if ( fragment instanceof IEditFragment< ? > )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         
         if ( how == FinishEditMode.SAVE )
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
               removeNoteFragmentById( fragment.getTag(),
                                       FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
            }
         }
      }
      
      return finished;
   }
   


   private void removeNoteFragmentById( String fragmentTag, int transit )
   {
      final Fragment fragment = findAddedFragmentByTag( fragmentTag );
      
      if ( fragment != null )
      {
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         transaction.remove( fragment );
         transaction.setTransition( transit );
         
         transaction.commit();
         
         final ViewGroup fragmentContainer = getFragmentContainer();
         final View taskNoteLayout = fragmentContainer.findViewWithTag( createTaskNoteLayoutTag( fragmentTag ) );
         
         if ( taskNoteLayout != null )
            fragmentContainer.removeView( taskNoteLayout );
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
            && ( (String) tag ).startsWith( TASK_NOTE_LAYOUT_TAG_STUB ) )
         {
            final View buttonsContainer = view.findViewById( R.id.note_buttons );
            if ( buttonsContainer != null )
               buttonsContainer.setVisibility( show ? View.VISIBLE : View.GONE );
         }
      }
   }
   


   private void createTaskFragment()
   {
      final Fragment fragment = TaskFragment.newInstance( createTaskFragmentConfiguration( getTaskIdFromIntent() ) );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      if ( findAddedFragmentById( R.id.frag_task ) == null )
         transaction.add( R.id.frag_task, fragment );
      else
         transaction.replace( R.id.frag_task, fragment );
      
      transaction.commit();
   }
   


   private Bundle createTaskFragmentConfiguration( String taskId )
   {
      final Bundle config = getFragmentConfigurations( R.id.frag_task );
      
      config.putString( TaskFragment.Config.TASK_ID, taskId );
      
      return config;
   }
   


   private void showNoteFragmentsOfTask( Task task )
   {
      if ( task.getNumberOfNotes() > 0 )
      {
         final ViewGroup fragmentContainer = getFragmentContainer();
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         final List< String > noteIds = task.getNoteIds();
         
         for ( int i = 0, cnt = noteIds.size(); i < cnt; ++i )
         {
            final String noteId = noteIds.get( i );
            final Fragment noteFragment = findAddedFragmentByTag( noteId );
            
            if ( noteFragment == null )
            {
               final int noteFragmentContainerId = createNoteFragmentContainerId( noteId );
               
               createNoteFragmentFromId( fragmentContainer,
                                         noteFragmentContainerId,
                                         noteId,
                                         transaction );
            }
         }
         
         transaction.commit();
      }
      
      removeDeletedNoteFragmentsOfTask( task );
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
   


   private void removeDeletedNoteFragmentsOfTask( Task task )
   {
      final List< String > noteIds = task.getNoteIds();
      final ViewGroup fragmentContainer = getFragmentContainer();
      
      for ( int i = 0, cntFragments = fragmentContainer.getChildCount(); i < cntFragments; ++i )
      {
         final View view = fragmentContainer.getChildAt( i );
         final Object tag = view.getTag();
         
         if ( tag instanceof String
            && ( (String) tag ).startsWith( TASK_NOTE_LAYOUT_TAG_STUB ) )
         {
            final ViewGroup taskNoteLayout = (ViewGroup) view;
            
            boolean found = false;
            for ( int j = 0, cntTaskNoteChilds = taskNoteLayout.getChildCount(); j < cntTaskNoteChilds
               && !found; ++j )
            {
               final View taskNoteChild = taskNoteLayout.getChildAt( j );
               final Object taskNoteChildTag = taskNoteChild.getTag();
               
               if ( taskNoteChildTag instanceof String )
               {
                  found = true;
                  
                  final String addedNoteId = (String) taskNoteChildTag;
                  final int noteFragmentContainerId = taskNoteChild.getId();
                  
                  if ( noteFragmentContainerId != NEW_NOTE_TEMPORARY_CONTAINER_ID
                     && !noteIds.contains( addedNoteId ) )
                  {
                     if ( noteFragmentContainerId == getConfiguredEditModeFragmentId() )
                     {
                        requestRemovingEditNoteFragment( addedNoteId );
                     }
                     else
                     {
                        removeNoteFragmentById( addedNoteId,
                                                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
                     }
                  }
               }
            }
         }
      }
   }
   


   private int createAddNewNoteFragment( Bundle fragmentConfig )
   {
      final int noteFragmentContainerId = createAddNewNoteFragmentContainer();
      
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      final Fragment fragment = NoteAddFragment.newInstance( fragmentConfig );
      
      transaction.add( noteFragmentContainerId, fragment, NEW_NOTE_TEMPORARY_ID );
      transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
      
      transaction.commit();
      
      return noteFragmentContainerId;
   }
   


   private void replaceEditNoteFragmentWithAddNoteFragment( String noteId )
   {
      final Task task = getTaskAssertNotNull();
      
      final NoteEditFragment noteEditFragment = (NoteEditFragment) findAddedFragmentByTag( noteId );
      final String currentTitle = noteEditFragment.getNoteTitle();
      final String currentText = noteEditFragment.getNoteText();
      
      removeNoteFragmentById( noteId, FragmentTransaction.TRANSIT_NONE );
      
      setActivityInEditMode( createAddNewNoteFragment( createAddNewNoteFragmentConfiguration( task.getTaskSeriesId(),
                                                                                              currentTitle,
                                                                                              currentText ) ) );
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
   


   private Bundle createAddNewNoteFragmentConfiguration( String taskSeriesId,
                                                         String noteTitle,
                                                         String noteText )
   {
      final Bundle config = createAddNewNoteFragmentConfiguration( taskSeriesId );
      
      if ( !TextUtils.isEmpty( noteTitle ) )
         config.putString( NoteAddFragment.Config.NEW_NOTE_TITLE, noteTitle );
      if ( !TextUtils.isEmpty( noteText ) )
         config.putString( NoteAddFragment.Config.NEW_NOTE_TEXT, noteText );
      
      return config;
   }
   


   private boolean finishAddingNewNote( FinishEditMode how )
   {
      final IEditFragment< ? > addNewNoteFragment = (IEditFragment< ? >) findAddedFragmentByTag( NEW_NOTE_TEMPORARY_ID );
      
      boolean ok = true;
      
      if ( how == FinishEditMode.SAVE )
         ok = addNewNoteFragment.onFinishEditing();
      else
         addNewNoteFragment.onCancelEditing();
      
      if ( ok )
         removeNoteFragmentById( NEW_NOTE_TEMPORARY_ID,
                                 FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
      
      return ok;
   }
   


   private View createAndAddNoteFragmentContainer( ViewGroup fragmentContainer,
                                                   int fragmentContainerId,
                                                   String noteId )
   {
      final View taskNote = getLayoutInflater().inflate( R.layout.task_note,
                                                         fragmentContainer,
                                                         false );
      taskNote.setTag( createTaskNoteLayoutTag( noteId ) );
      
      final View noteFragContainer = taskNote.findViewById( R.id.note_fragment_container );
      noteFragContainer.setId( fragmentContainerId );
      noteFragContainer.setTag( noteId );
      
      final RtmAuth.Perms accessLevel = AccountUtils.getAccessLevel( this );
      
      final View editNoteButton = taskNote.findViewById( R.id.note_buttons_edit );
      editNoteButton.setTag( noteId );
      accessLevel.setVisible( editNoteButton );
      
      final View deleteNoteButton = taskNote.findViewById( R.id.note_buttons_delete );
      deleteNoteButton.setTag( noteId );
      accessLevel.setVisible( deleteNoteButton );
      
      fragmentContainer.addView( taskNote );
      
      return noteFragContainer;
   }
   


   private void requestRemovingEditNoteFragment( final String noteId )
   {
      replaceEditNoteFragmentWithAddNoteFragment( noteId );
      
      UIUtils.newDialogWithActions( this,
                                    getString( R.string.task_dlg_removing_editing_note ),
                                    R.string.btn_edit,
                                    R.string.btn_delete,
                                    null,
                                    new Runnable()
                                    {
                                       @Override
                                       public void run()
                                       {
                                          finishAddingNewNote( FinishEditMode.FORCE_CANCELED );
                                          setActivityInEditMode( 0 );
                                       }
                                    } )
             .show();
   }
   


   private String createTaskNoteLayoutTag( String fragmentTag )
   {
      return TASK_NOTE_LAYOUT_TAG_STUB + fragmentTag;
   }
   


   private int createNoteFragmentContainerId( final String noteId )
   {
      return Integer.parseInt( noteId );
   }
   


   private Bundle createNoteFragmentConfiguration( String noteId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteFragment.Config.NOTE_ID, noteId );
      
      return config;
   }
   


   private ViewGroup getFragmentContainer()
   {
      return (ViewGroup) findViewById( R.id.fragment_container );
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
   protected int[] getFragmentIds()
   {
      return null;
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
      switch ( fragmentId )
      {
         case R.id.frag_task:
            if ( success )
            {
               handler.postAtFrontOfQueue( new Runnable()
               {
                  @Override
                  public void run()
                  {
                     showNoteFragmentsOfTask( getTaskAssertNotNull() );
                     invalidateOptionsMenu();
                  }
               } );
            }
            
         default :
            break;
      }
   }
}
