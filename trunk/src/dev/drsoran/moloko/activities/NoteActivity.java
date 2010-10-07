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
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;


public class NoteActivity extends Activity
{
   private final static String TAG = NoteActivity.class.getSimpleName();
   
   private List< RtmTaskNote > notes = Collections.emptyList();
   
   private int notePos = -1;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.note_activity );
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_VIEW ) )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Notes.CONTENT_URI );
         
         if ( client != null )
         {
            // Check if we should display a single note or all notes of a task
            final String taskId = intent.getStringExtra( Notes.TASKSERIES_ID );
            
            if ( taskId == null )
            {
               getSingleNote( client,
                              String.valueOf( ContentUris.parseId( intent.getData() ) ) );
            }
            else
            {
               getAllNotesOfTask( client,
                                  taskId,
                                  intent.getStringExtra( Notes._ID ) );
            }
            
            client.release();
            
            if ( notePos != -1 )
               displayNote( notes.get( notePos ) );
            
            updateUi();
         }
         else
         {
            // TODO: Show error
         }
      }
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
   


   private void evaluateNavButtons()
   {
      findViewById( R.id.note_btn_next ).setVisibility( ( hasNext() )
                                                                     ? View.VISIBLE
                                                                     : View.GONE );
      findViewById( R.id.note_btn_prev ).setVisibility( ( hasPrev() )
                                                                     ? View.VISIBLE
                                                                     : View.GONE );
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
   


   private void getSingleNote( ContentProviderClient client, String noteId )
   {
      // Query note
      try
      {
         final RtmTaskNote note = RtmNotesProviderPart.getNote( client, noteId );
         
         if ( note != null )
         {
            final ArrayList< RtmTaskNote > tmp = new ArrayList< RtmTaskNote >( 1 );
            tmp.add( note );
            
            initializeNotesList( tmp );
         }
         else
         {
            // TODO: Show error
         }
      }
      catch ( RemoteException e )
      {
         // TODO: Show error
      }
   }
   


   private void getAllNotesOfTask( ContentProviderClient client,
                                   String taskId,
                                   String startNoteId )
   {
      // Query all notes of task
      try
      {
         final RtmTaskNotes rtmTaskNotes = RtmNotesProviderPart.getAllNotes( client,
                                                                             taskId );
         
         if ( rtmTaskNotes != null )
         {
            initializeNotesList( rtmTaskNotes.getNotes() );
            
            if ( startNoteId != null )
            {
               boolean found = false;
               while ( notePos < notes.size() && !found )
               {
                  found = notes.get( notePos ).getId().equals( startNoteId );
                  if ( !found )
                     ++notePos;
               }
               
               if ( !found )
                  notePos = 0;
            }
         }
         else
         {
            // TODO: Show error
         }
      }
      catch ( RemoteException e )
      {
         // TODO: Show error
      }
   }
   


   private void initializeNotesList( List< RtmTaskNote > notes )
   {
      this.notes = Collections.unmodifiableList( notes );
      this.notePos = 0;
   }
   


   private void displayNote( RtmTaskNote note )
   {
      try
      {
         final TextView createdDate = (TextView) findViewById( R.id.note_created_date );
         createdDate.setText( MolokoDateUtils.formatDateTime( note.getCreated()
                                                                  .getTime(),
                                                              MolokoDateUtils.FORMAT_WITH_YEAR ) );
         
         // TODO: Handle return value
         UIUtils.initializeTitleWithTextLayout( findViewById( R.id.note ),
                                                note.getTitle(),
                                                note.getText() );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         // TODO: Show error
      }
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
