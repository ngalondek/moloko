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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.services.IDateFormatterService;


public class NoteEditFragment extends AbstractNoteEditFragment
{
   @InstanceState( key = Intents.Extras.KEY_TASK,
                   defaultValue = InstanceState.NO_DEFAULT )
   private Task task;
   
   @InstanceState( key = Intents.Extras.KEY_NOTE )
   private Note note;
   
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
   
   
   
   private void showNote( View content, Note note )
   {
      final TextView createdDate = (TextView) content.findViewById( R.id.note_created_date );
      createdDate.setText( getUiContext().getDateFormatter()
                                         .formatDateTime( note.getCreatedMillisUtc(),
                                                          IDateFormatterService.FORMAT_WITH_YEAR ) );
      title.setText( note.getTitle() );
      text.setText( note.getText() );
   }
   
   
   
   public Note getNoteAssertNotNull()
   {
      if ( note == null )
      {
         throw new AssertionError( "note must not be null" );
      }
      
      return note;
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      final Note note = getNoteAssertNotNull();
      
      return !note.getTitle().equals( UiUtils.getTrimmedText( title ) )
         || !note.getText().equals( UiUtils.getTrimmedText( text ) );
   }
   
   
   
   @Override
   public void onFinishEditing()
   {
      if ( listener != null )
      {
         final Note note = getNoteAssertNotNull();
         note.setTitle( UiUtils.getTrimmedText( this.title ) );
         note.setText( UiUtils.getTrimmedText( this.text ) );
         
         listener.onUpdateTasksNote( task, note );
      }
   }
}
