package dev.drsoran.moloko.activities;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.rtm.Task;


public class TasksListAdapter extends ArrayAdapter< Task >
{
   private final static String TAG = TasksListAdapter.class.getName();
   
   private final Context context;
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   private final Bundle configuration;
   
   private final Time now = new Time();
   
   

   public TasksListAdapter( Context context, int resourceId,
      List< Task > tasks, Bundle configuration )
   {
      super( context, 0, tasks );
      
      this.context = context;
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      this.configuration = ( configuration == null ) ? new Bundle()
                                                    : configuration;
      
      now.setToNow();
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = inflater.inflate( resourceId, parent, false );
      final View priority = view.findViewById( R.id.taskslist_listitem_priority );
      
      TextView description;
      TextView listName;
      TextView dueDate;
      CheckBox completed;
      ViewStub tagsStub;
      ViewStub notesStub;
      
      try
      {
         description = (TextView) view.findViewById( R.id.taskslist_listitem_desc );
         listName = (TextView) view.findViewById( R.id.taskslist_listitem_btn_list_name );
         dueDate = (TextView) view.findViewById( R.id.taskslist_listitem_due_date );
         completed = (CheckBox) view.findViewById( R.id.taskslist_listitem_check );
         tagsStub = (ViewStub) view.findViewById( R.id.taskslist_listitem_tags_stub );
         notesStub = (ViewStub) view.findViewById( R.id.taskslist_listitem_notes_stub );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final Task task = getItem( position );
      
      setDescription( description, task );
      
      setListName( listName, task );
      
      setDueDate( dueDate, task );
      
      setPriority( priority, task );
      
      // If we couldn't find the stub by ID it is already
      // inflated and it's ID has been replaced.
      if ( tagsStub != null )
         setTags( tagsStub, task );
      
      // If we couldn't find the stub by ID it is already
      // inflated and it's ID has been replaced.
      if ( notesStub != null )
         setNotes( view, notesStub, task );
      
      // Completed
      setCompleted( completed, task );
      
      return view;
   }
   


   private final void setDescription( TextView view, Task task )
   {
      view.setText( task.getName() );
      
      // description
      if ( task.getDue() != null )
      {
         final long dueDateMillis = task.getDue().getTime();
         
         // Make bold if the task is today
         if ( DateUtils.isToday( dueDateMillis ) )
         {
            view.setTypeface( Typeface.DEFAULT_BOLD );
            view.setText( task.getName() );
         }
         
         // Make underline and bold if overdue
         else
         {
            final Time dueTime = new Time();
            dueTime.set( task.getDue().getTime() );
            
            if ( now.after( dueTime ) )
            {
               final SpannableString content = new SpannableString( task.getName() );
               
               content.setSpan( new UnderlineSpan(), 0, content.length(), 0 );
               view.setTypeface( Typeface.DEFAULT_BOLD );
               view.setText( content );
            }
         }
      }
   }
   


   private final void setListName( TextView view, Task task )
   {
      view.setText( task.getListName() );
      
      if ( configuration.getBoolean( AbstractTasksListActivity.DISABLE_LIST_NAME ) )
      {
         view.setEnabled( false );
      }
   }
   


   private final void setDueDate( TextView view, Task task )
   {
      // if has a due date
      if ( task.getDue() != null )
      {
         String dueText = null;
         
         final long dueMillis = task.getDue().getTime();
         final boolean hasDueTime = task.hasDueTime();
         
         // Today
         if ( DateUtils.isToday( dueMillis ) )
         {
            // If it has a time, we show the time
            if ( hasDueTime )
               dueText = DateUtils.formatDateTime( context,
                                                   dueMillis,
                                                   DateUtils.FORMAT_SHOW_TIME );
            else
               // We only show the 'Today' phrase
               dueText = context.getString( R.string.phr_today );
         }
         else
         {
            final Time dueTime = new Time();
            dueTime.set( dueMillis );
            
            // If it is the same year
            if ( dueTime.year == now.year )
            {
               // If the same week and in the future
               if ( now.getWeekNumber() == dueTime.getWeekNumber()
                  && dueTime.after( now ) )
               {
                  // we only show the week day
                  dueText = DateUtils.getRelativeTimeSpanString( dueMillis,
                                                                 System.currentTimeMillis(),
                                                                 DateUtils.WEEK_IN_MILLIS,
                                                                 DateUtils.FORMAT_SHOW_WEEKDAY )
                                     .toString();
               }
               
               // Not the same week or same week but in the past
               else
               {
                  // we show the date but w/o year
                  dueText = DateUtils.formatDateTime( context,
                                                      dueMillis,
                                                      DateUtils.FORMAT_SHOW_DATE
                                                         | DateUtils.FORMAT_NO_YEAR );
               }
            }
            
            // Not the same year
            else
            {
               // we show the full date with year
               dueText = DateUtils.formatDateTime( context,
                                                   dueMillis,
                                                   DateUtils.FORMAT_SHOW_DATE );
            }
         }
         
         view.setText( dueText );
      }
      
      // has no due date
      else
         view.setVisibility( View.GONE );
   }
   


   private final void setPriority( View view, Task task )
   {
      switch ( task.getPriority() )
      {
         case High:
            view.setBackgroundResource( R.color.priority_1 );
            break;
         case Medium:
            view.setBackgroundResource( R.color.priority_2 );
            break;
         case Low:
            view.setBackgroundResource( R.color.priority_3 );
            break;
         case None:
         default :
            view.setBackgroundResource( R.color.priority_none );
            break;
      }
   }
   


   private void setTags( ViewStub tagsStub, Task task )
   {
      // If the task has no tags
      if ( task.getTags().size() == 0 )
      {
         tagsStub.setVisibility( View.GONE );
      }
      
      // inflate the stub and add tags
      else
      {
         try
         {
            final ViewGroup tagsContainer = (ViewGroup) tagsStub.inflate();
            
            if ( tagsContainer == null )
            {
               throw new Exception();
            }
            
            // Check if we should leave out a tag
            final String tagToHide = configuration.getString( AbstractTasksListActivity.HIDE_TAG_EQUALS );
            
            final List< String > tags = task.getTags();
            
            for ( String tagText : tags )
            {
               if ( tagToHide == null || !tagText.equalsIgnoreCase( tagToHide ) )
               {
                  final TextView tagView = (TextView) View.inflate( context,
                                                                    R.layout.taskslist_listitem_tag_button,
                                                                    null );
                  tagView.setText( tagText );
                  tagsContainer.addView( tagView );
               }
            }
         }
         catch ( Exception e )
         {
            Log.e( TAG, "Invalid layout spec.", e );
         }
      }
   }
   


   @SuppressWarnings( "unchecked" )
   private void setNotes( View listItem, ViewStub notesStub, Task task )
   {
      // If the task has no notes
      if ( task.getNumberOfNotes() == 0 )
      {
         notesStub.setVisibility( View.GONE );
      }
      
      // inflate the stub and add notes
      else
      {
         List< RtmTaskNote > notes = (List< RtmTaskNote >) listItem.getTag();
         
         if ( notes == null )
         {
            final ContentProviderClient client = context.getContentResolver()
                                                        .acquireContentProviderClient( Notes.CONTENT_URI );
            
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
         }
         
         if ( notes != null )
         {
            try
            {
               final ViewGroup notesContainer = (ViewGroup) notesStub.inflate();
               
               if ( notesContainer == null )
               {
                  throw new Exception();
               }
               
               for ( RtmTaskNote note : notes )
               {
                  final TextView tagView = (TextView) View.inflate( context,
                                                                    R.layout.taskslist_listitem_note_button,
                                                                    null );
                  tagView.setText( note.getText() );
                  notesContainer.addView( tagView );
               }
               
               listItem.setTag( notes );
            }
            catch ( Exception e )
            {
               Log.e( TAG, "Invalid layout spec.", e );
            }
         }
      }
      
   }
   


   private void setCompleted( CheckBox view, Task task )
   {
      view.setChecked( task.getCompleted() != null );
   }
   
}
