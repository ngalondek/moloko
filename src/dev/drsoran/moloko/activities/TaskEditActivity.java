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

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.AbstractTaskEditFragment;
import dev.drsoran.moloko.fragments.TaskEditFragment;
import dev.drsoran.moloko.fragments.base.AbstractPickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChangeTagsDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.DuePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.EstimatePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.RecurrPickerDialogFragment;
import dev.drsoran.moloko.fragments.factories.DefaultFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.IChangeTagsFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IPickerDialogListener;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class TaskEditActivity extends MolokoEditFragmentActivity implements
         ITaskEditFragmentListener, IChangeTagsFragmentListener,
         IPickerDialogListener
{
   @InstanceState( key = Intents.Extras.KEY_TASK,
                   defaultValue = InstanceState.NULL )
   private Task task;
   
   
   
   public TaskEditActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskEditActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.task_edit_activity );
      
      createTaskEditFragment();
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      getSupportMenuInflater().inflate( R.menu.edit_activity, menu );
      return true;
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
            onCancelEditingAndFinish();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   protected boolean onFinishActivityByHome()
   {
      return onCancelEditing();
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( onCancelEditing() )
      {
         super.onBackPressed();
      }
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
   protected void handleCancelWithChangesDialogClick( String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         finish();
      }
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_task_edit };
   }
   
   
   
   private void createTaskEditFragment()
   {
      final Fragment fragment = DefaultFragmentFactory.create( this,
                                                               TaskEditFragment.class,
                                                               createTaskEditFragmentConfig() );
      getSupportFragmentManager().beginTransaction()
                                 .add( R.id.frag_task_edit, fragment )
                                 .commit();
   }
   
   
   
   private void saveChangesAndFinish()
   {
      final AbstractTaskEditFragment taskEditFragment = getTaskEditFragment();
      final ApplyChangesInfo changes = taskEditFragment.onFinishEditing();
      
      boolean ok = true;
      if ( changes != null )
      {
         ok = applyModifications( changes );
      }
      
      if ( ok )
      {
         finish();
      }
   }
   
   
   
   private boolean onCancelEditing()
   {
      if ( isTaskChanged() )
      {
         UIUtils.showCancelWithChangesDialog( this );
         return false;
      }
      else
      {
         return true;
      }
   }
   
   
   
   private void onCancelEditingAndFinish()
   {
      if ( onCancelEditing() )
      {
         finish();
      }
   }
   
   
   
   private boolean isTaskChanged()
   {
      final AbstractTaskEditFragment taskEditFragment = getTaskEditFragment();
      return taskEditFragment.hasChanges();
   }
   
   
   
   private void showChangeTagsDialog( Bundle config )
   {
      final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      final DialogFragment newFragment = ChangeTagsDialogFragment.newInstance( config );
      
      newFragment.show( fragmentTransaction,
                        String.valueOf( R.id.frag_change_tags ) );
   }
   
   
   
   private AbstractTaskEditFragment getTaskEditFragment()
   {
      final AbstractTaskEditFragment taskEditFragment = (AbstractTaskEditFragment) findAddedFragmentById( R.id.frag_task_edit );
      return taskEditFragment;
   }
   
   
   
   private Bundle createTaskEditFragmentConfig()
   {
      final Bundle config = new Bundle();
      
      config.putParcelable( Intents.Extras.KEY_TASK, task );
      
      return config;
   }
   
   
   
   private Bundle createTaskEditChangeTagsConfiguration( List< String > tags )
   {
      final Bundle config = new Bundle( 2 );
      
      config.putStringArrayList( ChangeTagsDialogFragment.Config.TAGS,
                                 new ArrayList< String >( tags ) );
      
      return config;
   }
}
