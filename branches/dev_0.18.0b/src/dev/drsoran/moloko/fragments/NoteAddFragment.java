/* 
 *	Copyright (c) 2012 Ronny Röhricht
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
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart.NewNoteId;
import dev.drsoran.moloko.format.MolokoDateFormatter;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.rtm.Task;


public class NoteAddFragment extends AbstractNoteEditFragment
{
   private final static String NEW_NOTE_ID = "new_note_id";
   
   private final Time created = MolokoDateUtils.newTime();
   
   @InstanceState( key = Intents.Extras.KEY_TASK,
                   defaultValue = InstanceState.NO_DEFAULT )
   private Task task;
   
   @InstanceState( key = Intents.Extras.KEY_NOTE_TITLE,
                   defaultValue = Strings.EMPTY_STRING )
   private String newTitle;
   
   @InstanceState( key = Intents.Extras.KEY_NOTE_TEXT,
                   defaultValue = Strings.EMPTY_STRING )
   private String newText;
   
   @InstanceState( key = NEW_NOTE_ID, defaultValue = Strings.EMPTY_STRING )
   private String newNoteId;
   
   
   
   public final static NoteAddFragment newInstance( Bundle config )
   {
      final NoteAddFragment fragment = new NoteAddFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public NoteAddFragment()
   {
      registerAnnotatedConfiguredInstance( this, NoteAddFragment.class );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = super.onCreateView( inflater,
                                                    container,
                                                    savedInstanceState );
      
      showNote( fragmentView );
      
      return fragmentView;
   }
   
   
   
   private void showNote( View content )
   {
      final TextView createdDate = (TextView) content.findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateFormatter.formatDateTime( getSherlockActivity(),
                                                               created.toMillis( true ),
                                                               MolokoDateFormatter.FORMAT_WITH_YEAR ) );
      
      title.setText( newTitle );
      text.setText( newText );
      
      title.requestFocus();
   }
   
   
   
   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      newTitle = getNoteTitle();
      newText = getNoteText();
      
      super.onSaveInstanceState( outState );
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      if ( task == null )
         throw new AssertionError( "Expected task to be not null." );
      
      return task;
   }
   
   
   
   public String getNewNoteId()
   {
      return newNoteId;
   }
   
   
   
   private void setNewNoteId( String newNoteId )
   {
      this.newNoteId = newNoteId;
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      return !TextUtils.isEmpty( UIUtils.getTrimmedText( title ) )
         || !TextUtils.isEmpty( UIUtils.getTrimmedText( text ) );
   }
   
   
   
   @Override
   protected ApplyChangesInfo getChanges()
   {
      final NewNoteId newNoteId = createNewNoteId();
      final RtmTaskNote newNote = new RtmTaskNote( newNoteId.noteId,
                                                   getTaskAssertNotNull().getTaskSeriesId(),
                                                   new Date( created.toMillis( true ) ),
                                                   new Date( created.toMillis( true ) ),
                                                   null,
                                                   Strings.nullIfEmpty( UIUtils.getTrimmedText( title ) ),
                                                   Strings.nullIfEmpty( UIUtils.getTrimmedText( text ) ) );
      
      final ApplyChangesInfo modifications = NoteEditUtils.insertNote( getSherlockActivity(),
                                                                       newNote );
      if ( modifications.getActionItems() != null )
      {
         setNewNoteId( newNote.getId() );
      }
      
      return modifications;
   }
   
   
   
   private NewNoteId createNewNoteId()
   {
      return RtmNotesProviderPart.createNewNoteId( getSherlockActivity().getContentResolver()
                                                                        .acquireContentProviderClient( Notes.CONTENT_URI ) );
   }
}
