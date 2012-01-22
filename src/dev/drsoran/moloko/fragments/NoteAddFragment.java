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

package dev.drsoran.moloko.fragments;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart.NewNoteId;
import dev.drsoran.moloko.fragments.base.MolokoEditFragment;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;


public class NoteAddFragment extends MolokoEditFragment< NoteAddFragment >
{
   public static class Config
   {
      public final static String TASKSERIES_ID = Notes.TASKSERIES_ID;
      
      public final static String NEW_NOTE_TITLE = Notes.NOTE_TITLE;
      
      public final static String NEW_NOTE_TEXT = Notes.NOTE_TEXT;
      
      private final static String NEW_NOTE_ID = "new_note_id";
   }
   
   private EditText title;
   
   private EditText text;
   
   private final Time created = MolokoDateUtils.newTime();
   
   
   
   public final static NoteAddFragment newInstance( Bundle config )
   {
      final NoteAddFragment fragment = new NoteAddFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = createFragmentView( inflater,
                                                    container,
                                                    savedInstanceState );
      return fragmentView;
   }
   
   
   
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.note_edit_fragment,
                                                  container,
                                                  false );
      
      final TextView createdDate = (TextView) fragmentView.findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( getFragmentActivity(),
                                                           created.toMillis( true ),
                                                           MolokoDateUtils.FORMAT_WITH_YEAR ) );
      
      title = (EditText) fragmentView.findViewById( R.id.note_edit_title );
      text = (EditText) fragmentView.findViewById( R.id.note_edit_text );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      final Bundle configuration = getConfiguration();
      
      if ( configuration.containsKey( Config.NEW_NOTE_TITLE ) )
      {
         title.setText( configuration.getString( Config.NEW_NOTE_TITLE ) );
         configuration.remove( Config.NEW_NOTE_TITLE );
      }
      
      if ( configuration.containsKey( Config.NEW_NOTE_TEXT ) )
      {
         text.setText( configuration.getString( Config.NEW_NOTE_TEXT ) );
         configuration.remove( Config.NEW_NOTE_TEXT );
      }
      
      title.requestFocus();
   }
   
   
   
   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.TASKSERIES_ID ) )
         configuration.putString( Config.TASKSERIES_ID,
                                  config.getString( Config.TASKSERIES_ID ) );
      if ( config.containsKey( Config.NEW_NOTE_ID ) )
         configuration.putString( Config.NEW_NOTE_ID,
                                  config.getString( Config.NEW_NOTE_ID ) );
      if ( config.containsKey( Config.NEW_NOTE_TITLE ) )
         configuration.putString( Config.NEW_NOTE_TITLE,
                                  config.getString( Config.NEW_NOTE_TITLE ) );
      if ( config.containsKey( Config.NEW_NOTE_TEXT ) )
         configuration.putString( Config.NEW_NOTE_TEXT,
                                  config.getString( Config.NEW_NOTE_TEXT ) );
   }
   
   
   
   public String getConfiguredTaskSeriesId()
   {
      return configuration.getString( Config.TASKSERIES_ID );
   }
   
   
   
   public String getConfiguredNewNoteId()
   {
      return configuration.getString( Config.NEW_NOTE_ID );
   }
   
   
   
   private void setConfiguredNewNoteId( String newNoteId )
   {
      configuration.putString( Config.NEW_NOTE_ID, newNoteId );
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      if ( getView() != null )
         return !TextUtils.isEmpty( UIUtils.getTrimmedText( title ) )
            || !TextUtils.isEmpty( UIUtils.getTrimmedText( text ) );
      else
         return false;
   }
   
   
   
   @Override
   protected boolean saveChanges()
   {
      boolean ok = true;
      
      if ( getView() != null )
      {
         final NewNoteId newNoteId = createNewNoteId();
         final RtmTaskNote newNote = new RtmTaskNote( newNoteId.noteId,
                                                      getConfiguredTaskSeriesId(),
                                                      new Date( created.toMillis( true ) ),
                                                      new Date( created.toMillis( true ) ),
                                                      null,
                                                      Strings.nullIfEmpty( UIUtils.getTrimmedText( title ) ),
                                                      Strings.nullIfEmpty( UIUtils.getTrimmedText( text ) ) );
         
         final Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications = NoteEditUtils.insertNote( getFragmentActivity(),
                                                                                                                 newNote );
         ok = applyModifications( modifications );
         
         if ( ok )
         {
            setConfiguredNewNoteId( newNote.getId() );
         }
      }
      
      return ok;
   }
   
   
   
   private NewNoteId createNewNoteId()
   {
      return RtmNotesProviderPart.createNewNoteId( getFragmentActivity().getContentResolver()
                                                                        .acquireContentProviderClient( Notes.CONTENT_URI ) );
   }
   
   
   
   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      NoteFragment fragment = null;
      final String newNoteId = getConfiguredNewNoteId();
      
      if ( newNoteId != null )
      {
         final Bundle config = new Bundle();
         
         config.putString( NoteFragment.Config.NOTE_ID, newNoteId );
         
         fragment = NoteFragment.newInstance( config );
      }
      
      return fragment;
   }
}
