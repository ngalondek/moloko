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
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.rtm.Strings;


public class NoteAddFragment extends AbstractNoteEditFragment
{
   @InstanceState( key = "createdMillis" )
   private long createdMillisUtc;
   
   @InstanceState( key = Intents.Extras.KEY_TASK,
                   defaultValue = InstanceState.NO_DEFAULT )
   private Task task;
   
   @InstanceState( key = Intents.Extras.KEY_NOTE_TITLE,
                   defaultValue = Strings.EMPTY_STRING )
   private String newTitle;
   
   @InstanceState( key = Intents.Extras.KEY_NOTE_TEXT,
                   defaultValue = Strings.EMPTY_STRING )
   private String newText;
   
   private INoteAddFragmentListener listener;
   
   
   
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
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      createdMillisUtc = getUiContext().getCalendarProvider().getNowMillisUtc();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof INoteAddFragmentListener )
      {
         listener = (INoteAddFragmentListener) activity;
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
      
      showNote( fragmentView );
      
      return fragmentView;
   }
   
   
   
   private void showNote( View content )
   {
      final TextView createdDate = (TextView) content.findViewById( R.id.note_created_date );
      createdDate.setText( getUiContext().getDateFormatter()
                                         .formatDateTime( createdMillisUtc,
                                                          IDateFormatterService.FORMAT_WITH_YEAR ) );
      
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
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      if ( task == null )
      {
         throw new AssertionError( "Expected task to be not null." );
      }
      
      return task;
   }
   
   
   
   @Override
   public void onFinishEditing()
   {
      if ( listener != null )
      {
         final Note newNote = new Note( Constants.NO_ID, createdMillisUtc );
         newNote.setTitle( UiUtils.getTrimmedText( title ) );
         newNote.setText( UiUtils.getTrimmedText( text ) );
         
         task.addNote( newNote );
         
         listener.onAddTasksNote( task, newNote );
      }
   }
}
