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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;


public class NoteFragment extends AbstractNoteFragment implements
         IEditableFragment< NoteFragment >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + NoteFragment.class.getSimpleName();
   
   
   public static class Config extends AbstractNoteFragment.Config
   {
      public final static String NOTE = AbstractNoteFragment.Config.NOTE;
      
      public final static String NOTE_ID = AbstractNoteFragment.Config.NOTE_ID;
   }
   
   
   
   public final static NoteFragment newInstance( Bundle config )
   {
      final NoteFragment fragment = new NoteFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.note_fragment,
                                                  container,
                                                  false );
      return fragmentView;
   }
   
   
   
   @Override
   protected void showNote( View content, RtmTaskNote note )
   {
      final TextView createdDate = (TextView) content.findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( note.getCreatedDate()
                                                               .getTime(),
                                                           MolokoDateUtils.FORMAT_WITH_YEAR ) );
      
      if ( !UIUtils.initializeTitleWithTextLayout( content.findViewById( R.id.note ),
                                                   note.getTitle(),
                                                   note.getText() ) )
         throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
   }
   
   
   
   @Override
   public IEditFragment< ? extends Fragment > createEditFragmentInstance()
   {
      final Bundle config = new Bundle();
      
      if ( getConfiguredNote() != null )
         config.putParcelable( NoteEditFragment.Config.NOTE,
                               getConfiguredNote() );
      
      final NoteEditFragment fragment = NoteEditFragment.newInstance( config );
      return fragment;
   }
}
