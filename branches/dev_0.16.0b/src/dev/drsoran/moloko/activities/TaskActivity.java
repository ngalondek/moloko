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
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.dialogs.LocationChooser;
import dev.drsoran.moloko.fragments.NoteFragment;
import dev.drsoran.moloko.fragments.TaskFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.loaders.TaskLoader;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
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
   }
   

   protected static class OptionsMenu
   {
      public final static int EDIT_TASK = R.id.menu_edit_selected_tasks;
      
      public final static int DELETE_TASK = R.id.menu_delete_selected_tasks;
      
      public final static int POSTPONE_TASK = R.id.menu_postpone_selected_tasks;
      
      public final static int COMPLETE_TASK = R.id.menu_complete_selected_tasks;
      
      public final static int UNCOMPLETE_TASK = R.id.menu_uncomplete_selected_tasks;
   }
   
   private final static int NOTE_FRAGMENT_CONTAINER_ID_START = R.id.frag_note;
   
   private final static int TASK_LOADER_ID = 1;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_activity );
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_VIEW ) )
      {
         if ( getConfiguredTask() != null )
            showContent();
         else
         {
            final Uri taskUri = intent.getData();
            
            if ( taskUri != null )
            {
               final String taskId = taskUri.getLastPathSegment();
               final Bundle loaderConfig = new Bundle();
               loaderConfig.putString( Config.TASK_ID, taskId );
               
               getSupportLoaderManager().initLoader( TASK_LOADER_ID,
                                                     loaderConfig,
                                                     this );
            }
         }
      }
   }
   


   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      if ( config.containsKey( Config.TASK ) )
         configuration.putParcelable( Config.TASK,
                                      config.getParcelable( Config.TASK ) );
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
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final Task task = getConfiguredTask();
      
      if ( task != null && AccountUtils.isWriteableAccess( this ) )
      {
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.COMPLETE_TASK,
                                      getString( R.string.app_task_complete ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_button_task_checked_check,
                                      MenuItem.SHOW_AS_ACTION_ALWAYS,
                                      task.getCompleted() == null );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.UNCOMPLETE_TASK,
                                      getString( R.string.app_task_uncomplete ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_button_task_unchecked_check,
                                      MenuItem.SHOW_AS_ACTION_ALWAYS,
                                      task.getCompleted() != null );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.EDIT_TASK,
                                      getString( R.string.app_task_edit ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_button_task_edit,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      true );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.POSTPONE_TASK,
                                      getString( R.string.app_task_postpone ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_button_task_postponed,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      true );
         UIUtils.addOptionalMenuItem( this,
                                      menu,
                                      OptionsMenu.DELETE_TASK,
                                      getString( R.string.app_task_delete ),
                                      Menu.NONE,
                                      Menu.NONE,
                                      R.drawable.ic_button_task_trash,
                                      MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                      true );
      }
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      return super.onOptionsItemSelected( item );
   }
   


   @Override
   protected void updateContent( ViewGroup container )
   {
      super.updateContent( container );
      
      final Task task = getConfiguredTaskAssertNotNull();
      
      initTaskFragmentWithTask( task );
      createNoteFragmentsFromTask( task );
      
      invalidateOptionsMenu();
      
      // TODO: Repair
      // if ( task.getCompleted() != null )
      // {
      // completeTaskBtn.setImageResource( R.drawable.ic_button_task_unchecked_check );
      // }
      // else
      // {
      // completeTaskBtn.setImageResource( R.drawable.ic_button_task_checked_check );
      // }
   }
   


   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      boolean reloadTask = false;
      
      final Task task = getConfiguredTaskAssertNotNull();
      
      switch ( requestCode )
      {
         case TaskEditActivity.REQ_EDIT_TASK:
            switch ( resultCode )
            {
               case TaskEditActivity.RESULT_EDIT_TASK_CHANGED:
                  reloadTask = true;
                  break;
               
               default :
                  break;
            }
            break;
         
         case NoteEditActivity.REQ_EDIT_NOTE:
            switch ( resultCode )
            {
               case NoteEditActivity.RESULT_EDIT_NOTE_CHANGED:
                  reloadTask = true;
                  break;
               
               default :
                  break;
            }
            break;
         
         case AddNoteActivity.REQ_INSERT_NOTE:
            switch ( resultCode )
            {
               case AddNoteActivity.RESULT_INSERT_NOTE_OK:
                  reloadTask = true;
                  break;
               
               default :
                  break;
            }
            break;
         
         default :
            break;
      }
      
      if ( reloadTask )
      {
         // final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
         //
         // if ( client != null )
         // {
         // final Task oldTask = task;
         // task = TasksProviderPart.getTask( client, task.getId() );
         // client.release();
         //
         // if ( task == null )
         // task = oldTask;
         // }
         // else
         // {
         // LogUtils.logDBError( this, TAG, "Task" );
         // }
      }
   }
   


   public void onEditNote( View v )
   {
      startActivityForResult( Intents.createEditNoteIntent( this,
                                                            (String) v.getTag(),
                                                            true ),
                              NoteEditActivity.REQ_EDIT_NOTE );
   }
   


   // public void onDeleteNote( View v )
   // {
   // if ( task != null )
   // {
   // final String noteId = (String) v.getTag();
   //
   // UIUtils.newDeleteElementDialog( this,
   // getString( R.string.app_note ),
   // new Runnable()
   // {
   // @Override
   // public void run()
   // {
   // NoteEditUtils.deleteNote( TaskActivity.this,
   // noteId );
   // loadAndInflateNotes( taskContainer,
   // task );
   // }
   // },
   // null )
   // .show();
   // }
   // }
   
   private void initTaskFragmentWithTask( Task task )
   {
      final Fragment fragment = TaskFragment.newInstance( createTaskFragmentConfiguration( task ) );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      if ( getSupportFragmentManager().findFragmentById( R.id.frag_task ) == null )
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
   


   private void createNoteFragmentsFromTask( Task task )
   {
      if ( task.getNumberOfNotes() > 0 )
      {
         final ViewGroup fragmentContainer = (ViewGroup) findViewById( R.id.fragment_container );
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         final List< String > noteIds = task.getNoteIds();
         
         for ( int i = 0, cnt = noteIds.size(); i < cnt; ++i )
         {
            final String noteId = noteIds.get( i );
            
            final ViewGroup noteFragmentContainer = (ViewGroup) fragmentContainer.findViewWithTag( noteId );
            if ( noteFragmentContainer != null )
            {
               updateNoteFragmentWithId( noteFragmentContainer.getId(),
                                         noteId,
                                         transaction );
            }
            else
            {
               createNoteFragmentWithId( fragmentContainer,
                                         NOTE_FRAGMENT_CONTAINER_ID_START + i,
                                         noteId,
                                         transaction );
            }
         }
         
         transaction.commitAllowingStateLoss();
      }
   }
   


   private void createNoteFragmentWithId( ViewGroup fragmentContainer,
                                          int fragmentContainerId,
                                          String noteId,
                                          FragmentTransaction transaction )
   {
      final View taskNote = getLayoutInflater().inflate( R.layout.task_note,
                                                         fragmentContainer,
                                                         false );
      
      final View noteFragContainer = taskNote.findViewById( R.id.note_fragment_container );
      noteFragContainer.setId( fragmentContainerId );
      noteFragContainer.setTag( noteId );
      
      fragmentContainer.addView( taskNote );
      
      final Fragment fragment = NoteFragment.newInstance( createNoteFragmentConfiguration( noteId ) );
      transaction.add( fragmentContainerId, fragment, noteId );
   }
   


   private void updateNoteFragmentWithId( int fragmentContainerId,
                                          String noteId,
                                          FragmentTransaction transaction )
   {
      final Fragment fragment = NoteFragment.newInstance( createNoteFragmentConfiguration( noteId ) );
      transaction.replace( fragmentContainerId, fragment, noteId );
   }
   


   private Bundle createNoteFragmentConfiguration( String noteId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteFragment.Config.NOTE_ID, noteId );
      
      return config;
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
   


   public void onAddNote( String taskSeriesId )
   {
      startActivityForResult( Intents.createAddNoteIntent( this, taskSeriesId ),
                              AddNoteActivity.REQ_INSERT_NOTE );
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
