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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart.NewNoteId;
import dev.drsoran.moloko.util.AsyncInsertEntity;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;


public class AddNoteActivity extends Activity
{
   private final static String TAG = "Moloko."
      + AddNoteActivity.class.getSimpleName();
   
   public final static int REQ_INSERT_NOTE = 2;
   
   public final static int RESULT_INSERT_NOTE_FAILED = 1 << 0;
   
   public final static int RESULT_INSERT_NOTE_OK = 1 << 8;
   
   private long created;
   
   private String taskSeriesId;
   
   private EditText title;
   
   private EditText text;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.note_edit_activity );
      
      created = System.currentTimeMillis();
      
      final TextView createdDate = (TextView) findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( created,
                                                           MolokoDateUtils.FORMAT_WITH_YEAR ) );
      
      title = (EditText) findViewById( R.id.note_edit_title );
      text = (EditText) findViewById( R.id.note_edit_text );
      
      findViewById( R.id.note_btn_next ).setVisibility( View.INVISIBLE );
      findViewById( R.id.note_btn_prev ).setVisibility( View.INVISIBLE );
      
      final Intent intent = getIntent();
      
      taskSeriesId = intent.getExtras().getString( Notes.TASKSERIES_ID );
      
      if ( !intent.getAction().equals( Intent.ACTION_INSERT )
         || taskSeriesId == null )
      {
         UIUtils.initializeErrorWithIcon( this,
                                          R.string.err_unsupported_intent_action,
                                          intent.getAction() );
      }
   }
   


   public void onDone( View v )
   {
      if ( hasChanged() )
      {
         Uri newNoteUri = null;
         
         try
         {
            final Date createdDate = new Date();
            createdDate.setTime( created );
            
            final RtmTaskNote newNote = new RtmTaskNote( null,
                                                         taskSeriesId,
                                                         createdDate,
                                                         createdDate,
                                                         null,
                                                         Strings.nullIfEmpty( UIUtils.getTrimmedText( title ) ),
                                                         Strings.nullIfEmpty( UIUtils.getTrimmedText( text ) ) );
            newNoteUri = new AsyncInsertEntity< RtmTaskNote >( this )
            {
               @Override
               protected int getProgressMessageId()
               {
                  return R.string.dlg_insert_note;
               }
               


               @Override
               protected List< ContentProviderOperation > getInsertOperations( ContentResolver contentResolver,
                                                                               RtmTaskNote entity )
               {
                  final ContentProviderClient client = contentResolver.acquireContentProviderClient( Notes.CONTENT_URI );
                  
                  if ( client != null )
                  {
                     final NewNoteId newId = new NewNoteId();
                     final List< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >( 2 );
                     
                     operations.add( RtmNotesProviderPart.insertLocalCreatedNote( client,
                                                                                  entity,
                                                                                  newId ) );
                     client.release();
                     
                     operations.add( CreationsProviderPart.newCreation( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                                  newId.noteId ),
                                                                        entity.getCreatedDate()
                                                                              .getTime() ) );
                     return operations;
                  }
                  
                  return null;
               }
               


               @Override
               protected Uri getContentUri()
               {
                  return Notes.CONTENT_URI;
               }
               


               @Override
               protected String getPath()
               {
                  return Notes.PATH;
               }
            }.execute( newNote ).get();
         }
         catch ( InterruptedException e )
         {
            Log.e( TAG, "Failed to insert new note", e );
         }
         catch ( ExecutionException e )
         {
            Log.e( TAG, "Failed to insert new note", e );
         }
         
         if ( newNoteUri != null )
            setResult( RESULT_INSERT_NOTE_OK, getIntent().setData( newNoteUri ) );
         else
            setResult( RESULT_INSERT_NOTE_FAILED );
         
         finish();
      }
      else
      {
         setResult( RESULT_CANCELED );
         finish();
      }
   }
   


   public void onCancel( View v )
   {
      if ( hasChanged() )
      {
         UIUtils.newCancelWithChangesDialog( this, new Runnable()
         {
            public void run()
            {
               setResult( RESULT_CANCELED );
               finish();
            }
         }, null ).show();
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
   


   private boolean hasChanged()
   {
      return !TextUtils.isEmpty( UIUtils.getTrimmedText( title ) )
         || !TextUtils.isEmpty( UIUtils.getTrimmedText( text ) );
   }
}
