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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.AbstractTaskEditFragment;
import dev.drsoran.moloko.fragments.TaskAddFragment;
import dev.drsoran.moloko.fragments.TaskEditFragment;
import dev.drsoran.moloko.fragments.base.AbstractPickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.DuePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.EstimatePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.RecurrencePickerDialogFragment;
import dev.drsoran.moloko.fragments.factories.DefaultFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.IPickerDialogListener;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.Task;


public class TaskEditActivity extends AbstractTaskEditActivity implements
         ITaskEditFragmentListener, IPickerDialogListener
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
   public void onEditDueByPicker()
   {
      MolokoCalendar due = getTaskEditFragment().getDue();
      
      if ( due == null || !due.hasDate() )
      {
         due = MolokoCalendar.getInstance();
         due.setHasTime( false );
      }
      
      DuePickerDialogFragment.show( this, due.getTimeInMillis(), due.hasTime() );
   }
   
   
   
   @Override
   public void onEditRecurrenceByPicker()
   {
      Pair< String, Boolean > recurrencePattern = getTaskEditFragment().getRecurrencePattern();
      
      if ( recurrencePattern == null )
         recurrencePattern = Pair.create( Strings.EMPTY_STRING, Boolean.FALSE );
      
      RecurrencePickerDialogFragment.show( this,
                                           recurrencePattern.first,
                                           recurrencePattern.second );
   }
   
   
   
   @Override
   public void onEditEstimateByPicker()
   {
      final long estimateMillis = getTaskEditFragment().getEstimateMillis();
      EstimatePickerDialogFragment.show( this, estimateMillis );
   }
   
   
   
   @Override
   public void onPickerDialogClosed( AbstractPickerDialogFragment dialog,
                                     CloseReason reason )
   {
      if ( reason == CloseReason.OK )
      {
         if ( dialog instanceof DuePickerDialogFragment )
         {
            final DuePickerDialogFragment frag = (DuePickerDialogFragment) dialog;
            getTaskEditFragment().setDue( frag.getCalendar() );
         }
         else if ( dialog instanceof RecurrencePickerDialogFragment )
         {
            final RecurrencePickerDialogFragment frag = (RecurrencePickerDialogFragment) dialog;
            getTaskEditFragment().setRecurrencePattern( frag.getPattern() );
         }
         else if ( dialog instanceof EstimatePickerDialogFragment )
         {
            final EstimatePickerDialogFragment frag = (EstimatePickerDialogFragment) dialog;
            getTaskEditFragment().setEstimateMillis( frag.getMillis() );
         }
      }
   }
   
   
   
   @Override
   public void onBackgroundDeletion( Task oldTask )
   {
      new AlertDialogFragment.Builder( R.id.dlg_request_remove_by_bg_sync ).setMessage( getString( R.string.taskedit_activity_dlg_removing_editing ) )
                                                                           .setNeutralButton( R.string.btn_discard )
                                                                           .show( TaskEditActivity.this );
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      if ( dialogId == R.id.dlg_request_remove_by_bg_sync )
      {
         switch ( which )
         {
            case AlertDialog.BUTTON_NEUTRAL:
               setResult( RESULT_DELETED );
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
