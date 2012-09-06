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

import android.app.Activity;
import android.database.ContentObserver;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.format.MolokoDateFormatter;
import dev.drsoran.moloko.fragments.listeners.INoteEditFragmentListener;
import dev.drsoran.moloko.loaders.RtmTaskNoteLoader;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Notes;


public class NoteEditFragment extends AbstractNoteEditFragment implements
         LoaderCallbacks< RtmTaskNote >
{
   private final IHandlerToken handler = MolokoApp.acquireHandlerToken();
   
   @InstanceState( key = Intents.Extras.KEY_NOTE )
   private RtmTaskNote note;
   
   private ContentObserver noteChangesObserver;
   
   private INoteEditFragmentListener listener;
   
   
   
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
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof INoteEditFragmentListener )
      {
         listener = (INoteEditFragmentListener) activity;
      }
      else
      {
         listener = null;
      }
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      registerForNoteDeletedByBackgroundSync();
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
   public void onDestroy()
   {
      unregisterForNoteDeletedByBackgroundSync();
      handler.release();
      
      super.onDestroy();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
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
      createdDate.setText( MolokoDateFormatter.formatDateTime( getSherlockActivity(),
                                                               note.getCreatedDate()
                                                                   .getTime(),
                                                               MolokoDateFormatter.FORMAT_WITH_YEAR ) );
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
   
   
   
   @Override
   public Loader< RtmTaskNote > onCreateLoader( int id, Bundle args )
   {
      return new RtmTaskNoteLoader( getSherlockActivity(),
                                    getNoteAssertNotNull().getId() );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< RtmTaskNote > loader, RtmTaskNote data )
   {
      // If the database changed by a background sync and the assigned note
      // has been deleted, then we ask the user if he wants to keep his changes
      // or drop the note.
      if ( data == null )
      {
         handler.post( new Runnable()
         {
            @Override
            public void run()
            {
               if ( listener != null )
               {
                  listener.onBackgroundDeletion( getNoteAssertNotNull() );
               }
            }
         } );
      }
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< RtmTaskNote > loader )
   {
   }
   
   
   
   private void registerForNoteDeletedByBackgroundSync()
   {
      noteChangesObserver = new ContentObserver( null )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            getSherlockActivity().getSupportLoaderManager()
                                 .initLoader( RtmTaskNoteLoader.ID,
                                              Bundle.EMPTY,
                                              NoteEditFragment.this );
         }
      };
      
      getSherlockActivity().getContentResolver()
                           .registerContentObserver( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                               getNoteAssertNotNull().getId() ),
                                                     false,
                                                     noteChangesObserver );
   }
   
   
   
   private void unregisterForNoteDeletedByBackgroundSync()
   {
      getSherlockActivity().getContentResolver()
                           .unregisterContentObserver( noteChangesObserver );
      noteChangesObserver = null;
   }
}
