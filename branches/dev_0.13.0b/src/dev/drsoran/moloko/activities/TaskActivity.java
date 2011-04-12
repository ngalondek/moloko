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

import java.util.List;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.dialogs.LocationChooser;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Participant;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class TaskActivity extends Activity
{
   private final static String TAG = "Moloko."
      + TaskActivity.class.getSimpleName();
   
   public final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   private Task task;
   
   private ViewGroup taskContainer;
   
   private View priorityBar;
   
   private TextView addedDate;
   
   private TextView completedDate;
   
   private TextView source;
   
   private TextView postponed;
   
   private TextView description;
   
   private TextView listName;
   
   private ViewGroup tagsLayout;
   
   private View dateTimeSection;
   
   private View locationSection;
   
   private ViewGroup participantsSection;
   
   private View urlSection;
   
   private View editTaskBtn;
   
   private ImageButton completeTaskBtn;
   
   

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
            
            try
            {
               taskContainer = (ViewGroup) findViewById( R.id.task_container );
               priorityBar = taskContainer.findViewById( R.id.task_overview_priority_bar );
               addedDate = (TextView) taskContainer.findViewById( R.id.task_overview_added_date );
               completedDate = (TextView) taskContainer.findViewById( R.id.task_overview_completed_date );
               source = (TextView) taskContainer.findViewById( R.id.task_overview_src );
               postponed = (TextView) taskContainer.findViewById( R.id.task_overview_postponed );
               description = (TextView) taskContainer.findViewById( R.id.task_overview_desc );
               listName = (TextView) taskContainer.findViewById( R.id.task_overview_list_name );
               tagsLayout = (ViewGroup) taskContainer.findViewById( R.id.task_overview_tags );
               dateTimeSection = taskContainer.findViewById( R.id.task_dateTime );
               locationSection = taskContainer.findViewById( R.id.task_location );
               participantsSection = (ViewGroup) taskContainer.findViewById( R.id.task_participants );
               urlSection = taskContainer.findViewById( R.id.task_url );
               editTaskBtn = taskContainer.findViewById( R.id.task_buttons_edit );
               completeTaskBtn = (ImageButton) taskContainer.findViewById( R.id.task_buttons_complete );
               
               inflateNotes( taskContainer, task );
            }
            catch ( final ClassCastException e )
            {
               Log.e( TAG, "Invalid layout spec.", e );
               throw e;
            }
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
   


   @Override
   protected void onResume()
   {
      super.onResume();
      loadTask();
      
      // Deactivate controls which are not usable in read only mode
      if ( AccountUtils.isReadOnlyAccess( this ) )
         taskContainer.findViewById( R.id.task_buttons )
                      .setVisibility( View.GONE );
      else
         taskContainer.findViewById( R.id.task_buttons )
                      .setVisibility( View.VISIBLE );
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
      if ( task != null )
      {
         switch ( requestCode )
         {
            case TaskEditActivity.REQ_EDIT_TASK:
               switch ( resultCode )
               {
                  case TaskEditActivity.RESULT_EDIT_TASK_FAILED:
                     Toast.makeText( this,
                                     R.string.task_save_error,
                                     Toast.LENGTH_LONG ).show();
                     break;
                  
                  case TaskEditActivity.RESULT_EDIT_TASK_CHANGED:
                     final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
                     
                     if ( client != null )
                     {
                        final Task oldTask = task;
                        task = TasksProviderPart.getTask( client, task.getId() );
                        client.release();
                        
                        if ( task == null )
                           task = oldTask;
                        else
                           Toast.makeText( this,
                                           R.string.task_save_ok,
                                           Toast.LENGTH_LONG ).show();
                     }
                     else
                     {
                        LogUtils.logDBError( this, TAG, "Task" );
                     }
                     break;
                  
                  default :
                     break;
               }
               break;
            default :
               break;
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
      {
         TaskEditUtils.postponeTask( this, task );
      }
      
      finish();
   }
   


   public void onBack( View v )
   {
      finish();
   }
   


   private void loadTask()
   {
      if ( task != null )
      {
         UIUtils.setPriorityColor( priorityBar, task );
         
         addedDate.setText( MolokoDateUtils.formatDateTime( task.getAdded()
                                                                .getTime(),
                                                            FULL_DATE_FLAGS ) );
         
         if ( task.getCompleted() != null )
         {
            completedDate.setVisibility( View.VISIBLE );
            completedDate.setText( MolokoDateUtils.formatDateTime( task.getCompleted()
                                                                       .getTime(),
                                                                   FULL_DATE_FLAGS ) );
            completeTaskBtn.setImageResource( R.drawable.ic_list_unchecked_check );
         }
         else
         {
            completedDate.setVisibility( View.GONE );
            completeTaskBtn.setImageResource( R.drawable.ic_list_checked_check );
         }
         
         if ( task.getPosponed() > 0 )
         {
            postponed.setText( getString( R.string.task_postponed,
                                          task.getPosponed() ) );
            postponed.setVisibility( View.VISIBLE );
         }
         else
            postponed.setVisibility( View.GONE );
         
         if ( !TextUtils.isEmpty( task.getSource() ) )
         {
            String sourceStr = task.getSource();
            if ( sourceStr.equalsIgnoreCase( "js" ) )
               sourceStr = "web";
            
            source.setText( getString( R.string.task_source, sourceStr ) );
         }
         else
            source.setText( "?" );
         
         UIUtils.setTaskDescription( description, task, null );
         
         listName.setText( task.getListName() );
         
         UIUtils.inflateTags( this, tagsLayout, task.getTags(), null, null );
         
         setDateTimeSection( dateTimeSection, task );
         
         setLocationSection( locationSection, task );
         
         setParticipantsSection( participantsSection, task );
         
         if ( !TextUtils.isEmpty( task.getUrl() ) )
         {
            urlSection.setVisibility( View.VISIBLE );
            ( (TextView) urlSection.findViewById( R.id.title_with_text_text ) ).setText( task.getUrl() );
         }
         else
         {
            urlSection.setVisibility( View.GONE );
         }
         
         final RtmAuth.Perms permission = AccountUtils.getAccessLevel( this );
         permission.setEditable( editTaskBtn );
      }
   }
   


   private void setDateTimeSection( View view, Task task )
   {
      final boolean hasDue = task.getDue() != null;
      final boolean hasEstimate = !TextUtils.isEmpty( task.getEstimate() );
      final boolean isRecurrent = !TextUtils.isEmpty( task.getRecurrence() );
      
      // Check if we need this section
      if ( !hasEstimate && !hasDue && !isRecurrent )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         
         final StringBuilder textBuffer = new StringBuilder();
         
         if ( hasDue )
         {
            if ( task.hasDueTime() )
               UIUtils.appendAtNewLine( textBuffer,
                                        MolokoDateUtils.formatDateTime( task.getDue()
                                                                            .getTime(),
                                                                        MolokoDateUtils.FORMAT_WITH_YEAR
                                                                           | MolokoDateUtils.FORMAT_SHOW_WEEKDAY ) );
            else
               UIUtils.appendAtNewLine( textBuffer,
                                        MolokoDateUtils.formatDate( task.getDue()
                                                                        .getTime(),
                                                                    MolokoDateUtils.FORMAT_WITH_YEAR
                                                                       | MolokoDateUtils.FORMAT_SHOW_WEEKDAY ) );
            
         }
         
         if ( isRecurrent )
         {
            final String sentence = RecurrenceParsing.parseRecurrencePattern( task.getRecurrence(),
                                                                              task.isEveryRecurrence() );
            
            // In this case we add the 'repeat' to the beginning of the pattern, otherwise
            // the 'repeat' will be the header of the section.
            if ( hasDue || hasEstimate )
            {
               UIUtils.appendAtNewLine( textBuffer,
                                        getString( R.string.task_datetime_recurr_inline,
                                                   ( sentence != null
                                                                     ? sentence
                                                                     : getString( R.string.task_datetime_err_recurr ) ) ) );
            }
            else
            {
               UIUtils.appendAtNewLine( textBuffer,
                                        ( sentence != null
                                                          ? sentence
                                                          : getString( R.string.task_datetime_err_recurr ) ) );
            }
            
         }
         
         if ( hasEstimate )
         {
            UIUtils.appendAtNewLine( textBuffer,
                                     getString( R.string.task_datetime_estimate_inline,
                                                MolokoDateUtils.formatEstimated( this,
                                                                                 task.getEstimateMillis() ) ) );
         }
         
         // Determine the section title
         int titleId;
         
         if ( hasDue )
            titleId = R.string.task_datetime_title_due;
         else if ( hasEstimate )
            titleId = R.string.task_datetime_title_estimate;
         else
            titleId = R.string.task_datetime_title_recurr;
         
         if ( !UIUtils.initializeTitleWithTextLayout( view,
                                                      getString( titleId ),
                                                      textBuffer.toString() ) )
            throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
      }
   }
   


   private void setLocationSection( View view, final Task task )
   {
      String locationName = null;
      
      boolean showSection = !TextUtils.isEmpty( task.getLocationId() );
      
      if ( showSection )
      {
         // Tasks which are received by sharing from someone else may also have
         // a location ID set. But this ID is from the other ones DB. We identify
         // these tasks not by looking for the ID in our DB. These tasks do not
         // have a name and a location of 0.0, 0.0.
         //
         // @see: Issue 12: http://code.google.com/p/moloko/issues/detail?id=12
         locationName = task.getLocationName();
         
         showSection = !TextUtils.isEmpty( locationName )
            || Float.compare( task.getLongitude(), 0.0f ) != 0
            || Float.compare( task.getLatitude(), 0.0f ) != 0;
      }
      
      if ( !showSection )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         
         if ( TextUtils.isEmpty( locationName ) )
         {
            locationName = "Lon: " + task.getLongitude() + ", Lat: "
               + task.getLatitude();
         }
         
         boolean locationIsClickable = task.isViewable();
         
         if ( locationIsClickable )
         {
            final LocationChooser locationChooser = new LocationChooser( this,
                                                                         task );
            
            // Check if we can click the location
            if ( locationChooser.hasIntents() )
            {
               final SpannableString clickableLocation = new SpannableString( locationName );
               clickableLocation.setSpan( new ClickableSpan()
               {
                  @Override
                  public void onClick( View widget )
                  {
                     locationChooser.showChooser();
                  }
               }, 0, clickableLocation.length(), 0 );
               
               UIUtils.initializeTitleWithTextLayout( view,
                                                      getString( R.string.task_location ),
                                                      clickableLocation );
            }
            else
            {
               locationIsClickable = false;
            }
         }
         
         if ( !locationIsClickable )
         {
            if ( !UIUtils.initializeTitleWithTextLayout( view,
                                                         getString( R.string.task_location ),
                                                         locationName ) )
               throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
         }
      }
   }
   


   private void setParticipantsSection( ViewGroup view, Task task )
   {
      view.removeAllViews();
      
      final ParticipantList participants = task.getParticipants();
      
      if ( participants != null && participants.getCount() > 0 )
      {
         view.setVisibility( View.VISIBLE );
         
         for ( final Participant participant : participants.getParticipants() )
         {
            final SpannableString clickableContact = new SpannableString( participant.getFullname() );
            
            clickableContact.setSpan( new ClickableSpan()
            {
               @Override
               public void onClick( View widget )
               {
                  final Intent intent = Intents.createOpenContactIntent( TaskActivity.this,
                                                                         participant.getFullname(),
                                                                         participant.getUsername() );
                  
                  // It is possible that we came here from the ContactsListActivity
                  // by clicking a contact, clicking a task, clicking the contact again.
                  // So we reorder the former contact's tasks list to front.
                  intent.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
                  
                  startActivity( intent );
               }
            },
                                      0,
                                      clickableContact.length(),
                                      0 );
            
            final TextView textView = new TextView( this );
            UIUtils.applySpannable( textView, clickableContact );
            
            view.addView( textView );
         }
      }
      else
      {
         view.setVisibility( View.GONE );
      }
   }
   


   private void inflateNotes( ViewGroup taskContainer, Task task )
   {
      if ( task.getNumberOfNotes() > 0 )
      {
         List< RtmTaskNote > notes = null;
         
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Notes.CONTENT_URI );
         
         if ( client != null )
         {
            final RtmTaskNotes rtmNotes = RtmNotesProviderPart.getAllNotes( client,
                                                                            task.getTaskSeriesId() );
            if ( rtmNotes != null )
            {
               notes = rtmNotes.getNotes();
            }
            else
            {
               LogUtils.logDBError( this, TAG, "Notes" );
            }
            
            client.release();
         }
         
         if ( notes != null )
         {
            try
            {
               for ( final RtmTaskNote note : notes )
               {
                  final ViewGroup noteViewLayout = (ViewGroup) LayoutInflater.from( this )
                                                                             .inflate( R.layout.task_note,
                                                                                       taskContainer,
                                                                                       false );
                  
                  final View noteView = LayoutInflater.from( this )
                                                      .inflate( R.layout.note,
                                                                noteViewLayout,
                                                                true );
                  try
                  {
                     final TextView createdDate = (TextView) noteView.findViewById( R.id.note_created_date );
                     createdDate.setText( MolokoDateUtils.formatDateTime( note.getCreatedDate()
                                                                              .getTime(),
                                                                          FULL_DATE_FLAGS ) );
                  }
                  catch ( final ClassCastException e )
                  {
                     Log.e( TAG, "Invalid layout spec.", e );
                     throw e;
                  }
                  
                  if ( UIUtils.initializeTitleWithTextLayout( noteView,
                                                              note.getTitle(),
                                                              note.getText() ) )
                  {
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
         else
         {
            LogUtils.logDBError( this, TAG, "Notes" );
         }
      }
   }
}
