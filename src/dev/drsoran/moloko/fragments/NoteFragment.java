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
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoLoaderFragment;
import dev.drsoran.moloko.loaders.RtmTaskNoteLoader;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;


public class NoteFragment extends MolokoLoaderFragment< RtmTaskNote > implements
         IEditableFragment< NoteFragment >
{
   private final static int NOTE_LOADER_ID = 1;
   
   
   public static class Config
   {
      public final static String NOTE_ID = "note_id";
   }
   
   @InstanceState( key = Config.NOTE_ID, defaultValue = Strings.EMPTY_STRING )
   private String noteId;
   
   
   
   public final static NoteFragment newInstance( Bundle config )
   {
      final NoteFragment fragment = new NoteFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public NoteFragment()
   {
      registerAnnotatedConfiguredInstance( this, NoteFragment.class, null );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      configure( savedInstanceState );
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
   public void initContent( ViewGroup container )
   {
      final RtmTaskNote note = getLoaderDataAssertNotNull();
      
      final TextView createdDate = (TextView) container.findViewById( R.id.note_created_date );
      createdDate.setText( MolokoDateUtils.formatDateTime( getFragmentActivity(),
                                                           note.getCreatedDate()
                                                               .getTime(),
                                                           MolokoDateUtils.FORMAT_WITH_YEAR ) );
      
      if ( !UIUtils.initializeTitleWithTextLayout( container.findViewById( R.id.note ),
                                                   note.getTitle(),
                                                   note.getText() ) )
         throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
   }
   
   
   
   public String getNoteId()
   {
      return noteId;
   }
   
   
   
   public RtmTaskNote getNote()
   {
      return getLoaderData();
   }
   
   
   
   @Override
   public Loader< RtmTaskNote > newLoaderInstance( int id, Bundle args )
   {
      return new RtmTaskNoteLoader( getFragmentActivity(),
                                    args.getString( Config.NOTE_ID ) );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_note );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return NOTE_LOADER_ID;
   }
   
   
   
   @Override
   public boolean canBeEdited()
   {
      return getNote() != null
         && AccountUtils.isWriteableAccess( getFragmentActivity() );
   }
   
   
   
   @Override
   public IEditFragment< ? extends Fragment > createEditFragmentInstance()
   {
      if ( getLoaderData() != null )
      {
         final Bundle config = new Bundle();
         config.putParcelable( NoteEditFragment.Config.NOTE, getLoaderData() );
         
         final NoteEditFragment fragment = NoteEditFragment.newInstance( config );
         return fragment;
      }
      else
      {
         return null;
      }
   }
}
