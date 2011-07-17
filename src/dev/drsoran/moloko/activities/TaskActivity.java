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

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskActivity extends Activity
{
   private final static String TAG = "Moloko."
      + TaskActivity.class.getSimpleName();
   
   private Task task;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_VIEW ) )
      {
         final Uri taskUri = intent.getData();
         String taskId = null;
         
         if ( taskUri != null )
         {
            final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
            
            if ( client != null )
            {
               taskId = taskUri.getLastPathSegment();
               task = TasksProviderPart.getTask( client, taskId );
               
               client.release();
            }
            else
            {
               LogUtils.logDBError( this, TAG, "Task" );
            }
         }
         
         if ( task != null )
         {
            setContentView( R.layout.task_activity );
            
         }
         else
         {
            UIUtils.initializeErrorWithIcon( this,
                                             R.string.err_entity_not_found,
                                             getResources().getQuantityString( R.plurals.g_task,
                                                                               1 ) );
         }
      }
      else
      {
         UIUtils.initializeErrorWithIcon( this,
                                          R.string.err_unsupported_intent_action,
                                          intent.getAction() );
      }
   }
   
   
   
   public void onEditTask( View v )
   {
      if ( task != null )
         startActivityForResult( Intents.createEditTaskIntent( this,
                                                               task.getId() ),
                                 TaskEditActivity.REQ_EDIT_TASK );
   }
   
   
   
   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      boolean reloadTask = false;
      
      if ( task != null )
      {
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
      }
      
      if ( reloadTask )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
         
         if ( client != null )
         {
            final Task oldTask = task;
            task = TasksProviderPart.getTask( client, task.getId() );
            client.release();
            
            if ( task == null )
               task = oldTask;
         }
         else
         {
            LogUtils.logDBError( this, TAG, "Task" );
         }
      }
   }
   
   
   
   public void onCompleteTask( View v )
   {
      if ( task != null )
      {
         TaskEditUtils.setTaskCompletion( this,
                                          task,
                                          task.getCompleted() == null );
      }
      
      finish();
   }
   
   
   
   public void onPostponeTask( View v )
   {
      if ( task != null )
         TaskEditUtils.postponeTask( this, task );
      
      finish();
   }
   
   
   
   public void onDeleteTask( View v )
   {
      if ( task != null )
      {
         UIUtils.newDeleteElementDialog( this, task.getName(), new Runnable()
         {
            public void run()
            {
               TaskEditUtils.deleteTask( TaskActivity.this, task );
               finish();
            }
         }, null ).show();
      }
   }
   
   
   
   public void onEditNote( View v )
   {
      startActivityForResult( Intents.createEditNoteIntent( this,
                                                            (String) v.getTag(),
                                                            true ),
                              NoteEditActivity.REQ_EDIT_NOTE );
   }
   
   
   
   public void onAddNote( View v )
   {
      if ( task != null )
      {
         startActivityForResult( Intents.createAddNoteIntent( this,
                                                              task.getTaskSeriesId() ),
                                 AddNoteActivity.REQ_INSERT_NOTE );
      }
   }
   
   
   
   public void onDeleteNote( View v )
   {
      if ( task != null )
      {
         final String noteId = (String) v.getTag();
         
         UIUtils.newDeleteElementDialog( this,
                                         getString( R.string.app_note ),
                                         new Runnable()
                                         {
                                            public void run()
                                            {
                                               NoteEditUtils.deleteNote( TaskActivity.this,
                                                                         noteId );
                                               loadAndInflateNotes( taskContainer,
                                                                    task );
                                            }
                                         },
                                         null )
                .show();
      }
   }
   
   
   
   public void onBack( View v )
   {
      finish();
   }
   
   
   
   private void loadAndInflateNotes( ViewGroup taskContainer, Task task )
   {
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Notes.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmTaskNotes rtmNotes = RtmNotesProviderPart.getNotes( client,
                                                                      task.getTaskSeriesId() );
         client.release();
         
         if ( rtmNotes != null )
            this.rtmNotes = rtmNotes.getNotes();
         else
            LogUtils.logDBError( this, TAG, "Notes" );
         
         inflateNotes( taskContainer, task );
      }
   }
   
   
   
   private void inflateNotes( ViewGroup taskContainer, Task task )
   {
      UIUtils.removeTaggedViews( taskContainer, "note" );
      
      if ( rtmNotes != null && rtmNotes.size() > 0 )
      {
         try
         {
            for ( final RtmTaskNote note : rtmNotes )
            {
               final ViewGroup noteViewLayout = (ViewGroup) LayoutInflater.from( this )
                                                                          .inflate( R.layout.task_note,
                                                                                    taskContainer,
                                                                                    false );
               try
               {
                  final TextView createdDate = (TextView) noteViewLayout.findViewById( R.id.note_created_date );
                  createdDate.setText( MolokoDateUtils.formatDateTime( note.getCreatedDate()
                                                                           .getTime(),
                                                                       FULL_DATE_FLAGS ) );
                  
               }
               catch ( final ClassCastException e )
               {
                  Log.e( TAG, "Invalid layout spec.", e );
                  throw e;
               }
               
               if ( UIUtils.initializeTitleWithTextLayout( noteViewLayout,
                                                           note.getTitle(),
                                                           note.getText() ) )
               {
                  final View noteButtons = noteViewLayout.findViewById( R.id.note_buttons );
                  permission.setVisible( noteButtons );
                  
                  noteButtons.findViewById( R.id.note_buttons_edit )
                             .setTag( note.getId() );
                  noteButtons.findViewById( R.id.note_buttons_delete )
                             .setTag( note.getId() );
                  
                  taskContainer.addView( noteViewLayout );
               }
               else
               {
                  throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
               }
            }
         }
         catch ( final InflateException e )
         {
            Log.e( TAG, "Invalid layout spec.", e );
            throw e;
         }
      }
   }
}
