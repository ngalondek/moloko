package dev.drsoran.moloko.activities;

import java.util.List;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.format.DateUtils;
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
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskActivity extends Activity
{
   private final static String TAG = TaskActivity.class.getSimpleName();
   
   private final int FULL_DATE_FLAGS = DateUtils.LENGTH_MEDIUM
      | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME;
   
   

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
            
            // TODO: Show error if task could not be found.
            if ( task != null )
            {
               ViewGroup taskContainer;
               View priorityBar;
               TextView addedDate;
               TextView completedDate;
               TextView description;
               TextView listName;
               ViewGroup tagsLayout;
               View dateTime;
               
               try
               {
                  taskContainer = (ViewGroup) findViewById( R.id.task_container );
                  priorityBar = taskContainer.findViewById( R.id.task_overview_priority_bar );
                  addedDate = (TextView) taskContainer.findViewById( R.id.task_overview_added_date );
                  completedDate = (TextView) taskContainer.findViewById( R.id.task_overview_completed_date );
                  description = (TextView) taskContainer.findViewById( R.id.task_overview_desc );
                  listName = (TextView) taskContainer.findViewById( R.id.task_overview_list_name );
                  tagsLayout = (ViewGroup) taskContainer.findViewById( R.id.task_overview_tags );
                  dateTime = taskContainer.findViewById( R.id.task_dateTime );
               }
               catch ( ClassCastException e )
               {
                  Log.e( TAG, "Invalid layout spec.", e );
                  throw e;
               }
               
               addedDate.setText( DateUtils.formatDateTime( this,
                                                            task.getAdded()
                                                                .getTime(),
                                                            FULL_DATE_FLAGS ) );
               
               if ( task.getCompleted() != null )
                  completedDate.setText( DateUtils.formatDateTime( this,
                                                                   task.getCompleted()
                                                                       .getTime(),
                                                                   FULL_DATE_FLAGS ) );
               else
                  completedDate.setVisibility( View.GONE );
               
               UIUtils.setPriorityColor( priorityBar, task );
               
               UIUtils.setTaskDescription( description, task, null );
               
               listName.setText( task.getListName() );
               
               if ( taskContainer.getTag( R.id.task_overview_tags ) == null )
               {
                  UIUtils.inflateTags( this, tagsLayout, task, null, null );
                  taskContainer.setTag( R.id.task_overview_tags, "inflated" );
               }
               
               setDateTimeSection( dateTime, task );
               
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
            }
         }
      }
   }
   


   private final void setDateTimeSection( View view, Task task )
   {
      final boolean hasDue = task.getDue() != null;
      final boolean hasEstimate = !TextUtils.isEmpty( task.getEstimate() );
      
      // Check if we need this section
      if ( !hasEstimate && !hasDue )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         final StringBuffer textBuffer = new StringBuffer();
         
         if ( hasDue )
         {
            int flags = DateUtils.LENGTH_LONG | DateUtils.FORMAT_SHOW_DATE
               | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_YEAR;
            
            if ( task.hasDueTime() )
               flags |= DateUtils.FORMAT_SHOW_TIME;
            
            UIUtils.appendAtNewLine( textBuffer,
                                     DateUtils.formatDateTime( this,
                                                               task.getDue()
                                                                   .getTime(),
                                                               flags ) );
         }
         
         // TODO: Handle repeating tasks
         
         if ( hasEstimate )
         {
            UIUtils.appendAtNewLine( textBuffer, task.getEstimate() );
         }
         
         // TODO: Handle return value
         UIUtils.initializeTitleWithTextLayout( view,
                                                getString( ( hasDue )
                                                                     ? R.string.task_datetime_title_due
                                                                     : R.string.task_datetime_title_estimate ),
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
                                                                               task.getId() );
               if ( rtmNotes != null )
               {
                  notes = rtmNotes.getNotes();
               }
            }
            catch ( RemoteException e )
            {
               Log.e( TAG, "Unable to retrieve notes from DB for task ID "
                  + task.getId(), e );
            }
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
                     createdDate.setText( DateUtils.formatDateTime( this,
                                                                    note.getCreated()
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
