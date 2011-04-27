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

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.UIUtils;


public class NoteActivity extends AbstractNoteActivity
{
   @SuppressWarnings( "unused" )
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
   


   public void onEditNote( View v )
   {
      startActivity( Intents.createEditNoteIntent( this,
                                                   getNote().getId(),
                                                   false ) );
   }
   


   public void onDeleteNote( View v )
   {
      new AlertDialog.Builder( this ).setMessage( getString( R.string.phr_delete_note ) )
                                     .setPositiveButton( R.string.btn_delete,
                                                         new OnClickListener()
                                                         {
                                                            public void onClick( DialogInterface dialog,
                                                                                 int which )
                                                            {
                                                               NoteEditUtils.deleteNote( NoteActivity.this,
                                                                                         getNote().getId() );
                                                               removeCurrentNode();
                                                            }
                                                         } )
                                     .setNegativeButton( R.string.btn_cancel,
                                                         null )
                                     .show();
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
}
