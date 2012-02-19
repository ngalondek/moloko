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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.fragments.base.MolokoEditFragment;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;


public class NoteEditFragment extends MolokoEditFragment< NoteEditFragment >
{
   public static class Config
   {
      public final static String NOTE = "note";
   }
   
   @InstanceState( key = Config.NOTE )
   private RtmTaskNote note;
   
   private EditText title;
   
   private EditText text;
   
   
   
   public final static NoteEditFragment newInstance( Bundle config )
   {
      final NoteEditFragment fragment = new NoteEditFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.note_edit_fragment,
                                                  container,
                                                  false );
      
      title = (EditText) fragmentView.findViewById( R.id.note_edit_title );
      text = (EditText) fragmentView.findViewById( R.id.note_edit_text );
      
      showNote( fragmentView, getNoteAssertNotNull() );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      text.requestFocus();
   }
   
   
   
   public String getNoteTitle()
   {
      return title.getText().toString();
   }
   
   
   
   public String getNoteText()
   {
      return text.getText().toString();
   }
   
   
   
   private void showNote( View content, RtmTaskNote note )
   {
      final TextView createdDate = (TextView) content.findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( getFragmentActivity(),
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
   protected boolean saveChanges()
   {
      boolean ok = true;
      
      final String title = UIUtils.getTrimmedText( this.title );
      final String text = UIUtils.getTrimmedText( this.text );
      
      ok = !TextUtils.isEmpty( title ) || !TextUtils.isEmpty( text );
      
      if ( !ok )
      {
         this.text.requestFocus();
         Toast.makeText( getFragmentActivity(),
                         R.string.note_edit_toast_title_and_text_empty,
                         Toast.LENGTH_LONG ).show();
      }
      else
      {
         final RtmTaskNote note = getNoteAssertNotNull();
         final Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications = NoteEditUtils.setNoteTitleAndText( getFragmentActivity(),
                                                                                                                          note.getId(),
                                                                                                                          title,
                                                                                                                          text );
         applyModifications( modifications );
      }
      
      return ok;
   }
   
   
   
   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteFragment.Config.NOTE_ID,
                        getNoteAssertNotNull().getId() );
      
      final NoteFragment fragment = NoteFragment.newInstance( config );
      return fragment;
   }
}
