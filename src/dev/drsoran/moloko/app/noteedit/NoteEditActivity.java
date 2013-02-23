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

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.ui.activities.MolokoEditFragmentActivity;
import dev.drsoran.moloko.ui.fragments.IEditFragment;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.ui.fragments.factories.DefaultFragmentFactory;
import dev.drsoran.rtm.Task;


public class NoteEditActivity extends MolokoEditFragmentActivity implements
         INoteEditFragmentListener
{
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.note_edit_activity );
      createIntentDependentFragment();
   }
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      getSupportMenuInflater().inflate( R.menu.edit_activity, menu );
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   protected boolean onFinishActivityByHome()
   {
      return cancelFragmentEditing( getEditFragment() );
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( cancelFragmentEditing( getEditFragment() ) )
      {
         super.onBackPressed();
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_save:
            saveChangesAndFinish();
            return true;
            
         case R.id.menu_abort_edit:
            cancelEditingAndFinish();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   private void createIntentDependentFragment()
   {
      final String action = getIntent().getAction();
      if ( Intent.ACTION_EDIT.equals( action ) )
      {
         addFragment( NoteEditFragment.class );
      }
      else if ( Intent.ACTION_INSERT.equals( action ) )
      {
         addFragment( NoteAddFragment.class );
      }
      else
      {
         throw new UnsupportedOperationException( String.format( "Intent action unhandled: %s",
                                                                 getIntent().getAction() ) );
      }
   }
   
   
   
   private void saveChangesAndFinish()
   {
      if ( finishFragmentEditing( getEditFragment() ) )
      {
         finish();
      }
   }
   
   
   
   private void cancelEditingAndFinish()
   {
      if ( cancelFragmentEditing( getEditFragment() ) )
      {
         finish();
      }
   }
   
   
   
   @Override
   public void onBackgroundDeletion( RtmTaskNote oldNote )
   {
      new AlertDialogFragment.Builder( R.id.dlg_request_remove_by_bg_sync ).setMessage( getString( R.string.note_dlg_removing_editing_note ) )
                                                                           .setPositiveButton( R.string.btn_continue )
                                                                           .setNegativeButton( R.string.btn_discard )
                                                                           .show( NoteEditActivity.this );
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      if ( dialogId == R.id.dlg_request_remove_by_bg_sync )
      {
         switch ( which )
         {
            case AlertDialog.BUTTON_POSITIVE:
               replaceNoteEditFragmentWithNoteAddFragment();
               break;
            
            case AlertDialog.BUTTON_NEGATIVE:
               finish();
               break;
            
            default :
               break;
         }
      }
      else
      {
         super.onAlertDialogFragmentClick( dialogId, tag, which );
      }
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_note };
   }
   
   
   
   private void addFragment( Class< ? extends Fragment > clazz )
   {
      if ( findAddedFragmentById( R.id.frag_note ) == null )
      {
         final Fragment fragment = DefaultFragmentFactory.create( this,
                                                                  clazz,
                                                                  getIntent().getExtras() );
         
         getSupportFragmentManager().beginTransaction()
                                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                                    .add( R.id.frag_note, fragment )
                                    .commit();
      }
   }
   
   
   
   private void replaceNoteEditFragmentWithNoteAddFragment()
   {
      final NoteEditFragment noteEditFragment = (NoteEditFragment) findAddedFragmentById( R.id.frag_note );
      final String currentNoteTitle = noteEditFragment.getNoteTitle();
      final String currentNoteText = noteEditFragment.getNoteText();
      
      final Task task = getIntent().getParcelableExtra( Intents.Extras.KEY_TASK );
      
      final Bundle config = new Bundle();
      config.putParcelable( Intents.Extras.KEY_TASK, task );
      config.putString( Intents.Extras.KEY_NOTE_TITLE, currentNoteTitle );
      config.putString( Intents.Extras.KEY_NOTE_TEXT, currentNoteText );
      
      final Fragment noteAddFragment = NoteAddFragment.newInstance( config );
      
      getSupportFragmentManager().beginTransaction()
                                 .setTransition( FragmentTransaction.TRANSIT_NONE )
                                 .replace( R.id.frag_note, noteAddFragment )
                                 .commit();
   }
   
   
   
   private IEditFragment getEditFragment()
   {
      return (IEditFragment) findAddedFragmentById( R.id.frag_note );
   }
}
