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

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
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
import dev.drsoran.moloko.util.LocationChooser;
import dev.drsoran.moloko.util.MolokoDateUtils;
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
   
   private final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.task_activity );
      
      final Uri taskUri = getIntent().getData();
      
      // TODO: Show error if URI or ContentProviderClient is null.
      if ( taskUri != null )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
         
         if ( client != null )
         {
            final Task task = TasksProviderPart.getTask( client,
                                                         String.valueOf( ContentUris.parseId( taskUri ) ) );
            
            client.release();
            
            // TODO: Show error if task could not be found.
            if ( task != null )
            {
               ViewGroup taskContainer;
               View priorityBar;
               TextView addedDate;
               TextView completedDate;
               TextView source;
               TextView description;
               TextView listName;
               ViewGroup tagsLayout;
               View dateTimeSection;
               View locationSection;
               ViewGroup participantsSection;
               View urlSection;
               
               try
               {
                  taskContainer = (ViewGroup) findViewById( R.id.task_container );
                  priorityBar = taskContainer.findViewById( R.id.task_overview_priority_bar );
                  addedDate = (TextView) taskContainer.findViewById( R.id.task_overview_added_date );
                  completedDate = (TextView) taskContainer.findViewById( R.id.task_overview_completed_date );
                  source = (TextView) taskContainer.findViewById( R.id.task_overview_src );
                  description = (TextView) taskContainer.findViewById( R.id.task_overview_desc );
                  listName = (TextView) taskContainer.findViewById( R.id.task_overview_list_name );
                  tagsLayout = (ViewGroup) taskContainer.findViewById( R.id.task_overview_tags );
                  dateTimeSection = taskContainer.findViewById( R.id.task_dateTime );
                  locationSection = taskContainer.findViewById( R.id.task_location );
                  participantsSection = (ViewGroup) taskContainer.findViewById( R.id.task_participants );
                  urlSection = taskContainer.findViewById( R.id.task_url );
               }
               catch ( ClassCastException e )
               {
                  Log.e( TAG, "Invalid layout spec.", e );
                  throw e;
               }
               
               UIUtils.setPriorityColor( priorityBar, task );
               
               addedDate.setText( MolokoDateUtils.formatDateTime( task.getAdded()
                                                                      .getTime(),
                                                                  FULL_DATE_FLAGS ) );
               
               if ( task.getCompleted() != null )
                  completedDate.setText( MolokoDateUtils.formatDateTime( task.getCompleted()
                                                                             .getTime(),
                                                                         FULL_DATE_FLAGS ) );
               else
                  completedDate.setVisibility( View.GONE );
               
               if ( !TextUtils.isEmpty( task.getSource() ) )
               {
                  String sourceStr = task.getSource();
                  if ( sourceStr.equalsIgnoreCase( "js" ) )
                     sourceStr = "Web";
                  
                  source.setText( getString( R.string.task_source, sourceStr ) );
               }
               else
                  source.setVisibility( View.GONE );
               
               UIUtils.setTaskDescription( description, task, null );
               
               listName.setText( task.getListName() );
               
               if ( taskContainer.getTag( R.id.task_overview_tags ) == null )
               {
                  UIUtils.inflateTags( this, tagsLayout, task, null, null );
                  taskContainer.setTag( R.id.task_overview_tags, "inflated" );
               }
               
               setDateTimeSection( dateTimeSection, task );
               
               setLocationSection( locationSection, task );
               
               setParticipantsSection( participantsSection, task );
               
               // This is necessary due to a problem with attribute
               // autoLink="all". This causes to render the text
               // wrongly after orientation change. We prevent this
               // by remember ourself if this was inflated already.
               if ( taskContainer.getTag( R.id.task_note ) == null )
               {
                  inflateNotes( taskContainer, task );
                  taskContainer.setTag( R.id.task_note, "inflated" );
               }
               
               if ( !TextUtils.isEmpty( task.getUrl() ) )
               {
                  ( (TextView) urlSection.findViewById( R.id.title_with_text_text ) ).setText( task.getUrl() );
               }
               else
               {
                  urlSection.setVisibility( View.GONE );
               }
            }
         }
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
         final StringBuffer textBuffer = new StringBuffer();
         
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
         
         // TODO: Handle return value
         UIUtils.initializeTitleWithTextLayout( view,
                                                getString( titleId ),
                                                textBuffer.toString() );
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
            || ( task.getLongitude() != 0.0f || task.getLatitude() != 0.0f );
      }
      
      if ( !showSection )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
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
            UIUtils.initializeTitleWithTextLayout( view,
                                                   getString( R.string.task_location ),
                                                   locationName );
         }
      }
   }
   


   private void setParticipantsSection( ViewGroup view, Task task )
   {
      final ParticipantList participants = task.getParticipants();
      
      if ( participants != null && participants.getCount() > 0 )
      {
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
            try
            {
               final RtmTaskNotes rtmNotes = RtmNotesProviderPart.getAllNotes( client,
                                                                               task.getTaskSeriesId() );
               if ( rtmNotes != null )
               {
                  notes = rtmNotes.getNotes();
               }
            }
            catch ( RemoteException e )
            {
               Log.e( TAG, "Unable to retrieve notes from DB for task ID "
                  + task.getTaskSeriesId(), e );
            }
            
            client.release();
         }
         
         if ( notes != null )
         {
            try
            {
               for ( RtmTaskNote note : notes )
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
                     createdDate.setText( MolokoDateUtils.formatDateTime( note.getCreated()
                                                                              .getTime(),
                                                                          FULL_DATE_FLAGS ) );
                  }
                  catch ( ClassCastException e )
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
                     // TODO: Show error
                  }
               }
            }
            catch ( InflateException e )
            {
               Log.e( TAG, "Invalid layout spec.", e );
            }
         }
         else
         {
            // TODO: Show error
         }
      }
   }
   
}