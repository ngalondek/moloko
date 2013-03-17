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

package dev.drsoran.moloko.app.noteedit;

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

import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.content.ApplyChangesInfo;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Notes;


class NoteEditFragment extends AbstractNoteEditFragment implements
         LoaderCallbacks< RtmTaskNote >
{
   @InstanceState( key = Intents.Extras.KEY_NOTE )
   private RtmTaskNote note;
   
   private ContentObserver noteChangesObserver;
   
   private INoteEditFragmentListener listener;
   
   private IHandlerToken handler;
   
   
   
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
      
      handler = getUiContext().acquireHandlerToken();
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
      
      if ( handler != null )
      {
         handler.release();
         handler = null;
      }
      
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
      createdDate.setText( getUiContext().getDateFormatter()
                                         .formatDateTime( note.getCreatedDate()
                                                              .getTime(),
                                                          IDateFormatterService.FORMAT_WITH_YEAR ) );
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
                                   Strings.nullIfEmpty( UiUtils.getTrimmedText( title ) ) )
         || SyncUtils.hasChanged( Strings.nullIfEmpty( note.getText() ),
                                  Strings.nullIfEmpty( UiUtils.getTrimmedText( text ) ) );
   }
   
   
   
   @Override
   protected ApplyChangesInfo getChanges()
   {
      final String title = UiUtils.getTrimmedText( this.title );
      final String text = UiUtils.getTrimmedText( this.text );
      
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
      noteChangesObserver = new ContentObserver( getUiContext().asSystemContext()
                                                               .getHandler() )
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
                           .registerContentObserver( DbUtils.contentUriWithId( Notes.CONTENT_URI,
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
