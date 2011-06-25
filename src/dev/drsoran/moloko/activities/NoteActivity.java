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

import android.content.ContentProviderClient;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;


public class NoteActivity extends AbstractNoteActivity
{
   private final static String TAG = "Moloko."
      + NoteActivity.class.getSimpleName();
   
   
   
   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.note_activity );
      
      final Intent intent = getIntent();
      
      if ( !intent.getAction().equals( Intent.ACTION_VIEW ) )
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
      
      restrictUIToRtmAccessLevel();
   }
   
   
   
   public void onEditNote( View v )
   {
      startActivity( Intents.createEditNoteIntent( this,
                                                   getNote().getId(),
                                                   false ) );
   }
   
   
   
   public void onAddNote( View v )
   {
      startActivityForResult( Intents.createAddNoteIntent( this,
                                                           getNote().getTaskSeriesId() ),
                              AddNoteActivity.REQ_INSERT_NOTE );
   }
   
   
   
   public void onDeleteNote( View v )
   {
      UIUtils.newDeleteElementDialog( this,
                                      getString( R.string.app_note ),
                                      new Runnable()
                                      {
                                         public void run()
                                         {
                                            NoteEditUtils.deleteNote( NoteActivity.this,
                                                                      getNote().getId() );
                                            removeCurrentNode();
                                         }
                                      },
                                      null )
             .show();
   }
   
   
   
   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      if ( requestCode == AddNoteActivity.REQ_INSERT_NOTE )
      {
         switch ( resultCode )
         {
            case AddNoteActivity.RESULT_INSERT_NOTE_OK:
               final Uri noteUri = data.getData();
               
               if ( noteUri != null )
               {
                  final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Notes.CONTENT_URI );
                  
                  if ( client != null )
                  {
                     final RtmTaskNote note = RtmNotesProviderPart.getNote( client,
                                                                            noteUri.getLastPathSegment() );
                     client.release();
                     
                     if ( note != null )
                     {
                        insertNewNote( note );
                     }
                     else
                     {
                        Log.e( TAG, "Invalid URI after note insert: " + noteUri );
                     }
                  }
                  else
                  {
                     LogUtils.logDBError( this, TAG, "Notes" );
                  }
               }
               else
               {
                  Log.e( TAG, "Result URI is null after note insert" );
               }
               break;
            
            default :
               break;
         }
      }
      else
         super.onActivityResult( requestCode, resultCode, data );
   }
   
   
   
   @Override
   protected void displayNote( RtmTaskNote note )
   {
      super.displayNote( note );
      
      if ( !UIUtils.initializeTitleWithTextLayout( findViewById( R.id.note ),
                                                   note.getTitle(),
                                                   note.getText() ) )
         throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
   }
   
   
   
   private void restrictUIToRtmAccessLevel()
   {
      findViewById( R.id.note_buttons ).setVisibility( AccountUtils.isReadOnlyAccess( this )
                                                                                            ? View.GONE
                                                                                            : View.VISIBLE );
   }
}
