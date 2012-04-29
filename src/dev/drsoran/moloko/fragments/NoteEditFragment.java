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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;


public class NoteEditFragment extends AbstractNoteEditFragment
{
   @InstanceState( key = Intents.Extras.KEY_NOTE )
   private RtmTaskNote note;
   
   
   
   public final static NoteEditFragment newInstance( Bundle config )
   {
      final NoteEditFragment fragment = new NoteEditFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public NoteEditFragment()
   {
      registerAnnotatedConfiguredInstance( this, NoteEditFragment.class );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = super.onCreateView( inflater,
                                                    container,
                                                    savedInstanceState );
      
      showNote( fragmentView, getNoteAssertNotNull() );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      text.requestFocus();
   }
   
   
   
   @Override
   public String getNoteTitle()
   {
      return title.getText().toString();
   }
   
   
   
   @Override
   public String getNoteText()
   {
      return text.getText().toString();
   }
   
   
   
   private void showNote( View content, RtmTaskNote note )
   {
      final TextView createdDate = (TextView) content.findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( getSherlockActivity(),
                                                           note.getCreatedDate()
                                                               .getTime(),
                                                           MolokoDateUtils.FORMAT_WITH_YEAR ) );
      title.setText( note.getTitle() );
      text.setText( note.getText() );
   }
   
   
   
   public RtmTaskNote getNoteAssertNotNull()
   {
      if ( note == null )
         throw new IllegalStateException( "note must not be null" );
      
      return note;
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      final RtmTaskNote note = getNoteAssertNotNull();
      
      return SyncUtils.hasChanged( Strings.nullIfEmpty( note.getTitle() ),
                                   Strings.nullIfEmpty( UIUtils.getTrimmedText( title ) ) )
         || SyncUtils.hasChanged( Strings.nullIfEmpty( note.getText() ),
                                  Strings.nullIfEmpty( UIUtils.getTrimmedText( text ) ) );
   }
   
   
   
   @Override
   protected ApplyChangesInfo getChanges()
   {
      final String title = UIUtils.getTrimmedText( this.title );
      final String text = UIUtils.getTrimmedText( this.text );
      
      final RtmTaskNote note = getNoteAssertNotNull();
      final ApplyChangesInfo modifications = NoteEditUtils.setNoteTitleAndText( getSherlockActivity(),
                                                                                note.getId(),
                                                                                title,
                                                                                text );
      
      return modifications;
   }
}
