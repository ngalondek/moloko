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

import android.content.ContentProviderClient;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.rtm.ListTask;


public class TasksListAdapter extends ArrayAdapter< ListTask >
{
   private final static String TAG = "Moloko."
      + TasksListAdapter.class.getName();
   
   private final Context context;
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   private final Bundle configuration;
   
   private final Time now = MolokoDateUtils.newTime();
   
   

   public TasksListAdapter( Context context, int resourceId,
      List< ListTask > tasks, Bundle configuration )
   {
      super( context, 0, tasks );
      
      this.context = context;
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      this.configuration = ( configuration == null ) ? new Bundle()
                                                    : configuration;
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = inflater.inflate( resourceId, parent, false );
      final View priority = view.findViewById( R.id.taskslist_listitem_priority );
      
      TextView description;
      ImageView recurrent;
      TextView listName;
      TextView dueDate;
      ImageView completed;
      ViewGroup tagsLayout;
      ViewStub notesStub;
      
      try
      {
         description = (TextView) view.findViewById( R.id.taskslist_listitem_desc );
         listName = (TextView) view.findViewById( R.id.taskslist_listitem_btn_list_name );
         recurrent = (ImageView) view.findViewById( R.id.taskslist_listitem_recurrent );
         dueDate = (TextView) view.findViewById( R.id.taskslist_listitem_due_date );
         completed = (ImageView) view.findViewById( R.id.taskslist_listitem_check );
         tagsLayout = (ViewGroup) view.findViewById( R.id.taskslist_listitem_tags );
         notesStub = (ViewStub) view.findViewById( R.id.taskslist_listitem_notes_stub );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final ListTask task = getItem( position );
      
      UIUtils.setTaskDescription( description, task, now );
      
      if ( task.getRecurrence() != null )
         recurrent.setVisibility( View.VISIBLE );
      
      setListName( listName, task );
      
      setDueDate( dueDate, task );
      
      UIUtils.setPriorityColor( priority, task );
      
      UIUtils.inflateTags( context,
                           tagsLayout,
                           task,
                           configuration,
                           (OnClickListener) context );
      
      // If we couldn't find the stub by ID it is already
      // inflated and it's ID has been replaced.
      if ( notesStub != null )
         setNotes( view, notesStub, task );
      
      // Completed
      setCompleted( completed, task );
      
      return view;
   }
   


   private final void setListName( TextView view, ListTask task )
   {
      view.setText( task.getListName() );
      
      if ( configuration.getBoolean( AbstractTasksListActivity.DISABLE_LIST_NAME ) )
      {
         view.setEnabled( false );
      }
   }
   


   private final void setDueDate( TextView view, ListTask task )
   {
      // if has a due date
      if ( task.getDue() != null )
      {
         String dueText = null;
         
         final long dueMillis = task.getDue().getTime();
         final boolean hasDueTime = task.hasDueTime();
         
         // Today
         if ( MolokoDateUtils.isToday( dueMillis ) )
         {
            // If it has a time, we show the time
            if ( hasDueTime )
               dueText = MolokoDateUtils.formatTime( dueMillis );
            else
               // We only show the 'Today' phrase
               dueText = context.getString( R.string.phr_today );
         }
         else
         {
            final Time dueTime = MolokoDateUtils.newTime( dueMillis );
            
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
                  dueText = MolokoDateUtils.formatDate( dueMillis, 0 );
               }
            }
            
            // Not the same year
            else
            {
               // we show the full date with year
               dueText = MolokoDateUtils.formatDate( dueMillis,
                                                     MolokoDateUtils.FORMAT_WITH_YEAR );
            }
         }
         
         view.setText( dueText );
      }
      
      // has no due date
      else
         view.setVisibility( View.GONE );
   }
   


   private void setNotes( View listItem, ViewStub notesStub, ListTask task )
   {
      // If the task has no notes
      if ( task.getNumberOfNotes() == 0 )
      {
         notesStub.setVisibility( View.GONE );
      }
      
      // inflate the stub and add notes
      else
      {
         List< RtmTaskNote > notes = task.getNotes();
         
         if ( notes.size() == 0 )
         {
            final ContentProviderClient client = context.getContentResolver()
                                                        .acquireContentProviderClient( Notes.CONTENT_URI );
            
            if ( client != null )
            {
               try
               {
                  final RtmTaskNotes rtmNotes = RtmNotesProviderPart.getAllNotes( client,
                                                                                  task.getTaskSeriesId() );
                  client.release();
                  
                  if ( rtmNotes != null )
                  {
                     notes = rtmNotes.getNotes();
                     task.setNotes( notes );
                  }
               }
               catch ( RemoteException e )
               {
                  Log.e( TAG, "Unable to retrieve notes from DB for task ID "
                     + task.getTaskSeriesId(), e );
               }
            }
         }
         
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
         }
         catch ( Exception e )
         {
            Log.e( TAG, "Invalid layout spec.", e );
         }
      }
   }
   


   private void setCompleted( ImageView view, ListTask task )
   {
      view.setEnabled( task.getCompleted() != null );
   }
   
}
