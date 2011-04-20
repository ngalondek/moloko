/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;


abstract class AbstractNoteActivity extends Activity
{
   private final static String TAG = "Moloko."
      + AbstractNoteActivity.class.getSimpleName();
   
   public final static String EXTRA_SHOW_ALL_NOTES_OF_TASK = "show_all_notes_of _task";
   
   private final static String NOTE_POS = "note_pos";
   
   protected List< RtmTaskNote > notes = Collections.emptyList();
   
   protected int notePos = -1;
   
   

   @Override
   protected void onResume()
   {
      super.onResume();
      
      boolean found = false;
      final Intent intent = getIntent();
      final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Notes.CONTENT_URI );
      
      if ( client != null )
      {
         // Check if we should display a single note or all notes of a task
         final String taskId = intent.getStringExtra( Notes.TASKSERIES_ID );
         final boolean showAll = intent.getBooleanExtra( EXTRA_SHOW_ALL_NOTES_OF_TASK,
                                                         Boolean.FALSE );
         
         if ( taskId == null && !showAll )
         {
            found = getSingleNote( client, intent.getData()
                                                 .getLastPathSegment() );
         }
         else if ( showAll )
         {
            found = getAllNotesOfTask( client,
                                       null,
                                       intent.getData().getLastPathSegment() );
         }
         else
         {
            found = getAllNotesOfTask( client,
                                       taskId,
                                       intent.getStringExtra( Notes._ID ) );
         }
         
         client.release();
         
         if ( found )
         {
            if ( notePos != -1 )
               displayNote( notes.get( notePos ) );
            
            updateUi();
         }
      }
      else
      {
         LogUtils.logDBError( this, TAG, "Notes" );
      }
      
      if ( !found )
      {
         UIUtils.initializeErrorWithIcon( this,
                                          R.string.err_entity_not_found,
                                          getString( R.string.app_note ) );
      }
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle savedInstanceState )
   {
      super.onRestoreInstanceState( savedInstanceState );
      
      if ( savedInstanceState != null )
         notePos = savedInstanceState.getInt( NOTE_POS, -1 );
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      
      if ( outState != null )
         outState.putInt( NOTE_POS, notePos );
   }
   


   public void onNextNote( View view )
   {
      displayNote( getNext() );
      updateUi();
   }
   


   public void onPrevNote( View view )
   {
      displayNote( getPrev() );
      updateUi();
   }
   


   public void onClose( View view )
   {
      finish();
   }
   


   public RtmTaskNote getNote()
   {
      return notes.get( notePos );
   }
   


   protected void displayNote( RtmTaskNote note )
   {
      final TextView createdDate = (TextView) findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( note.getCreatedDate()
                                                               .getTime(),
                                                           MolokoDateUtils.FORMAT_WITH_YEAR ) );
   }
   


   private void evaluateNavButtons()
   {
      findViewById( R.id.note_btn_next ).setVisibility( ( hasNext() )
                                                                     ? View.VISIBLE
                                                                     : View.INVISIBLE );
      findViewById( R.id.note_btn_prev ).setVisibility( ( hasPrev() )
                                                                     ? View.VISIBLE
                                                                     : View.INVISIBLE );
   }
   


   private void updateUi()
   {
      evaluateNavButtons();
      
      if ( notes.size() > 1 )
      {
         setTitle( getString( R.string.app_note ) + " (" + ( notePos + 1 )
            + "/" + notes.size() + ")" );
      }
   }
   


   private boolean getSingleNote( ContentProviderClient client, String noteId )
   {
      boolean found = false;
      
      // Query note
      final RtmTaskNote note = RtmNotesProviderPart.getNote( client, noteId );
      
      if ( note != null )
      {
         final ArrayList< RtmTaskNote > tmp = new ArrayList< RtmTaskNote >( 1 );
         tmp.add( note );
         
         initializeNotesList( tmp );
         
         found = true;
      }
      else
      {
         LogUtils.logDBError( this, TAG, "Notes" );
      }
      
      return found;
   }
   


   private boolean getAllNotesOfTask( ContentProviderClient client,
                                      String taskId,
                                      String startNoteId )
   {
      boolean found = false;
      
      if ( taskId == null )
      {
         final RtmTaskNote note = RtmNotesProviderPart.getNote( client,
                                                                startNoteId );
         if ( note != null )
            taskId = note.getTaskSeriesId();
      }
      
      if ( taskId == null )
         return false;
      
      // Query all notes of task
      final RtmTaskNotes rtmTaskNotes = RtmNotesProviderPart.getAllNotes( client,
                                                                          taskId );
      
      if ( rtmTaskNotes != null )
      {
         found = true;
         
         // Check if we have a notePos from a saved instance state.
         if ( notePos != -1 && notePos < notes.size() )
         {
            initializeNotesList( notes, notePos );
         }
         else
         {
            initializeNotesList( rtmTaskNotes.getNotes() );
            
            boolean foundStartNote = false;
            
            while ( startNoteId != null && notePos < notes.size()
               && !foundStartNote )
            {
               foundStartNote = notes.get( notePos )
                                     .getId()
                                     .equals( startNoteId );
               if ( !foundStartNote )
                  ++notePos;
            }
            
            if ( !foundStartNote )
               notePos = 0;
         }
      }
      else
      {
         LogUtils.logDBError( this, TAG, "Notes" );
      }
      
      return found;
   }
   


   private void initializeNotesList( List< RtmTaskNote > notes )
   {
      this.notes = Collections.unmodifiableList( notes );
      this.notePos = 0;
   }
   


   private void initializeNotesList( List< RtmTaskNote > notes, int notePos )
   {
      this.notes = Collections.unmodifiableList( notes );
      this.notePos = notePos;
   }
   


   private boolean hasNext()
   {
      return notePos != -1 && notePos < notes.size() - 1;
   }
   


   private RtmTaskNote getNext()
   {
      return notes.get( ++notePos );
   }
   


   private boolean hasPrev()
   {
      return notePos > 0;
   }
   


   private RtmTaskNote getPrev()
   {
      return notes.get( --notePos );
   }
}
