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

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.AbstractTaskEditFragment;
import dev.drsoran.moloko.fragments.TaskAddFragment;
import dev.drsoran.moloko.fragments.TaskEditFragment;
import dev.drsoran.moloko.fragments.base.AbstractPickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChangeTagsDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.DuePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.EstimatePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.RecurrPickerDialogFragment;
import dev.drsoran.moloko.fragments.factories.DefaultFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.IChangeTagsFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IPickerDialogListener;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.Task;


public class TaskEditActivity extends AbstractTaskEditActivity implements
         ITaskEditFragmentListener, IChangeTagsFragmentListener,
         IPickerDialogListener
{
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
      showChangeTagsDialog( createTaskEditChangeTagsConfiguration( tags ) );
   }
   
   
   
   @Override
   public void onTagsChanged( List< String > tags )
   {
      final AbstractTaskEditFragment taskEditFragment = getTaskEditFragment();
      taskEditFragment.setTags( tags );
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
      
      RecurrPickerDialogFragment.show( this,
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
         else if ( dialog instanceof RecurrPickerDialogFragment )
         {
            final RecurrPickerDialogFragment frag = (RecurrPickerDialogFragment) dialog;
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
               setResult( R.integer.activity_result_deleted );
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
   
   
   
   private void showChangeTagsDialog( Bundle config )
   {
      final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      final DialogFragment newFragment = ChangeTagsDialogFragment.newInstance( config );
      
      newFragment.show( fragmentTransaction,
                        String.valueOf( R.id.frag_change_tags ) );
   }
   
   
   
   private Bundle createTaskEditChangeTagsConfiguration( List< String > tags )
   {
      final Bundle config = new Bundle( 2 );
      
      config.putStringArrayList( ChangeTagsDialogFragment.Config.TAGS,
                                 new ArrayList< String >( tags ) );
      
      return config;
   }
}
