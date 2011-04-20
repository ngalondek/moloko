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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;


public class NoteEditActivity extends AbstractNoteActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + NoteEditActivity.class.getSimpleName();
   
   public final static int REQ_EDIT_NOTE = 1;
   
   public final static int RESULT_EDIT_NOTE_FAILED = 1 << 0;
   
   public final static int RESULT_EDIT_NOTE_OK = 1 << 8;
   
   public final static int RESULT_EDIT_NOTE_CHANGED = 1 << 9
      | RESULT_EDIT_NOTE_OK;
   
   private EditText title;
   
   private EditText text;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.note_edit_activity );
      
      final Intent intent = getIntent();
      
      if ( !intent.getAction().equals( Intent.ACTION_EDIT ) )
      {
         UIUtils.initializeErrorWithIcon( this,
                                          R.string.err_unsupported_intent_action,
                                          intent.getAction() );
      }
      else
      {
         title = (EditText) findViewById( R.id.note_edit_title );
         text = (EditText) findViewById( R.id.note_edit_text );
      }
   }
   


   public void onDone( View v )
   {
      if ( hasChanged() )
      {
         new AlertDialog.Builder( this ).setMessage( R.string.phr_edit_dlg_done )
                                        .setPositiveButton( android.R.string.yes,
                                                            new OnClickListener()
                                                            {
                                                               public void onClick( DialogInterface dialog,
                                                                                    int which )
                                                               {
                                                                  final boolean ok = NoteEditUtils.setNoteTitleAndText( NoteEditActivity.this,
                                                                                                                        getNote().getId(),
                                                                                                                        Strings.nullIfEmpty( title.getText() )
                                                                                                                               .toString(),
                                                                                                                        Strings.nullIfEmpty( text.getText() )
                                                                                                                               .toString() );
                                                                  setResult( ok
                                                                               ? RESULT_EDIT_NOTE_CHANGED
                                                                               : RESULT_EDIT_NOTE_FAILED );
                                                                  finish();
                                                               }
                                                            } )
                                        .setNegativeButton( android.R.string.no,
                                                            null )
                                        .show();
      }
      else
      {
         setResult( RESULT_EDIT_NOTE_OK );
         finish();
      }
   }
   


   public void onCancel( View v )
   {
      if ( hasChanged() )
      {
         new AlertDialog.Builder( this ).setMessage( R.string.phr_edit_dlg_cancel )
                                        .setPositiveButton( android.R.string.yes,
                                                            new OnClickListener()
                                                            {
                                                               public void onClick( DialogInterface dialog,
                                                                                    int which )
                                                               {
                                                                  setResult( RESULT_CANCELED );
                                                                  finish();
                                                               }
                                                            } )
                                        .setNegativeButton( android.R.string.no,
                                                            null )
                                        .show();
      }
      else
      {
         setResult( RESULT_CANCELED );
         finish();
      }
   }
   


   @Override
   public void onBackPressed()
   {
      onCancel( null );
   }
   


   @Override
   protected void displayNote( RtmTaskNote note )
   {
      super.displayNote( note );
      
      title.setText( note.getTitle() );
      text.setText( note.getText() );
   }
   


   private boolean hasChanged()
   {
      final RtmTaskNote note = getNote();
      
      return SyncUtils.hasChanged( note.getTitle(), title.getText().toString() )
         || SyncUtils.hasChanged( note.getText(), text.getText().toString() );
   }
}
