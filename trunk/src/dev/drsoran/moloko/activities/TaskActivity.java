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
import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
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
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskActivity extends Activity
{
   private final static String TAG = TaskActivity.class.getSimpleName();
   
   private final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.task_activity );
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
      
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
               
               // TODO: setLocationSection
               
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
   


   private final void setDateTimeSection( View view, Task task )
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
