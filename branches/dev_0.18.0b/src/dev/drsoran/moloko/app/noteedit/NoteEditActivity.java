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

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.baseactivities.MolokoEditActivity;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.ui.fragments.IEditFragment;


public class NoteEditActivity extends MolokoEditActivity implements
         INoteEditFragmentListener, INoteAddFragmentListener
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
      getMenuInflater().inflate( R.menu.edit_activity, menu );
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
   public void onUpdateTasksNote( Task updatedTask, Note updatedNote )
   {
      getAppContext().getContentEditService().updateTask( this, updatedTask );
   }
   
   
   
   @Override
   public void onAddTasksNote( Task task, Note addedNote )
   {
      getAppContext().getContentEditService().updateTask( this, task );
   }
   
   
   
   private void addFragment( Class< ? extends Fragment > clazz )
   {
      if ( findAddedFragmentById( R.id.frag_note ) == null )
      {
         final Fragment fragment = Fragment.instantiate( this,
                                                         clazz.getName(),
                                                         getIntent().getExtras() );
         
         getFragmentManager().beginTransaction()
                             .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                             .add( R.id.frag_note, fragment )
                             .commit();
      }
   }
   
   
   
   private IEditFragment getEditFragment()
   {
      return (IEditFragment) findAddedFragmentById( R.id.frag_note );
   }
}
