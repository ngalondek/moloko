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

package dev.drsoran.moloko.app.taskedit;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.fragments.factories.DefaultFragmentFactory;


public class TaskEditActivity extends AbstractTaskEditActivity implements
         ITaskEditFragmentListener
{
   public final static int REQ_DEFAULT = 0;
   
   public final static int RESULT_DELETED = -10;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_edit_activity );
      createIntentDependentFragment();
   }
   
   
   
   @Override
   public void onChangeTags( List< String > tags )
   {
      startActivityForResult( Intents.createChooseTagsIntent( tags ),
                              REQ_DEFAULT );
   }
   
   
   
   @Override
   public void onEditDueByPicker( Due due,
                                  IValueChangedListener valueChangedListener )
   {
      if ( due == null )
      {
         due = Due.EMPTY;
      }
      
      final DuePickerDialogFragment frag = DuePickerDialogFragment.show( this,
                                                                         due );
      frag.setValueChangedListener( valueChangedListener );
   }
   
   
   
   @Override
   public void onEditRecurrenceByPicker( Recurrence recurrence,
                                         IValueChangedListener valueChangedListener )
   {
      if ( recurrence == null )
      {
         recurrence = Recurrence.EMPTY;
      }
      
      final RecurrencePickerDialogFragment frag = RecurrencePickerDialogFragment.show( this,
                                                                                       recurrence );
      frag.setValueChangedListener( valueChangedListener );
   }
   
   
   
   @Override
   public void onEditEstimateByPicker( long estimation,
                                       IValueChangedListener valueChangedListener )
   {
      final long estimateMillis = getTaskEditFragment().getEstimateMillis();
      final EstimatePickerDialogFragment frag = EstimatePickerDialogFragment.show( this,
                                                                                   estimateMillis );
      frag.setValueChangedListener( valueChangedListener );
   }
   
   
   
   @Override
   protected void onActivityResult( int req, int result, Intent data )
   {
      if ( req == REQ_DEFAULT )
      {
         if ( result == RESULT_OK )
         {
            onTagsChanged( data.getStringArrayListExtra( Intents.Extras.KEY_TAGS ) );
         }
      }
      
      super.onActivityResult( req, result, data );
   }
   
   
   
   private void onTagsChanged( List< String > tags )
   {
      final AbstractTaskEditFragment taskEditFragment = getTaskEditFragment();
      taskEditFragment.setTags( tags );
   }
   
   
   
   private void createIntentDependentFragment()
   {
      final String action = getIntent().getAction();
      if ( Intent.ACTION_EDIT.equals( action ) )
      {
         addFragment( TaskEditFragment.class );
      }
      else if ( Intent.ACTION_INSERT.equals( action ) )
      {
         addFragment( TaskAddFragment.class );
      }
      else
      {
         throw new UnsupportedOperationException( String.format( "Intent action unhandled: %s",
                                                                 getIntent().getAction() ) );
      }
   }
   
   
   
   private void addFragment( Class< ? extends Fragment > clazz )
   {
      if ( findAddedFragmentById( R.id.frag_task_edit ) == null )
      {
         final Fragment fragment = DefaultFragmentFactory.create( this,
                                                                  clazz,
                                                                  getIntent().getExtras() );
         
         getSupportFragmentManager().beginTransaction()
                                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                                    .add( R.id.frag_task_edit, fragment )
                                    .commit();
      }
   }
}
