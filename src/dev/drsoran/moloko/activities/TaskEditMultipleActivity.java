/*
 * Copyright (c) 2011 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.activities;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.TaskEditMultipleFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoMenuItemBuilder;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class TaskEditMultipleActivity extends MolokoEditFragmentActivity
         implements ITaskEditFragmentListener
{
   private static class OptionsMenu
   {
      public final static int SAVE = R.id.menu_save;
      
      public final static int ABORT = R.id.menu_abort_edit;
   }
   
   
   private enum FinishEditMode
   {
      SAVE, CANCELED
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_edit_multiple_activity );
      
      final List< Task > tasks = getConfiguredTasksFromIntentConfigAssertNotNull();
      
      setTitle( getString( R.string.edit_multiple_tasks_titlebar,
                           tasks.size(),
                           getResources().getQuantityString( R.plurals.g_task,
                                                             tasks.size() ) ) );
      
      if ( savedInstanceState == null )
         createTaskEditMultipleFragment( getIntent().getExtras() );
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final boolean hasRtmWriteAccess = AccountUtils.isWriteableAccess( this );
      
      final MolokoMenuItemBuilder builder = new MolokoMenuItemBuilder();
      
      builder.setItemId( OptionsMenu.SAVE )
             .setTitle( getString( R.string.app_save ) )
             .setIconId( R.drawable.ic_menu_disc )
             .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_ALWAYS )
             .setShow( hasRtmWriteAccess )
             .build( menu );
      
      builder.setItemId( OptionsMenu.ABORT )
             .setTitle( getString( android.R.string.cancel ) )
             .setIconId( R.drawable.ic_menu_cancel )
             .build( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.SAVE:
            if ( finishEditing( FinishEditMode.SAVE ) )
               finish();
            
            return true;
            
         case OptionsMenu.ABORT:
            if ( finishEditing( FinishEditMode.CANCELED ) )
               finish();
            
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( finishEditing( FinishEditMode.CANCELED ) )
         super.onBackPressed();
   }
   
   
   
   @Override
   public void onChangeTags( List< String > tags )
   {
   }
   
   
   
   @Override
   public void onEditDueByPicker()
   {
   }
   
   
   
   @Override
   public void onEditRecurrenceByPicker()
   {
   }
   
   
   
   @Override
   public void onEditEstimateByPicker()
   {
   }
   
   
   
   private boolean finishEditing( FinishEditMode how )
   {
      boolean finished = true;
      
      switch ( how )
      {
         case SAVE:
            finished = saveChanges();
            break;
         
         case CANCELED:
            finished = cancelChanges();
            break;
         
         default :
            break;
      }
      
      return finished;
   }
   
   
   
   @Override
   protected boolean onFinishActivityByHome()
   {
      return finishEditing( FinishEditMode.CANCELED );
   }
   
   
   
   private boolean saveChanges()
   {
      final TaskEditMultipleFragment taskEditMultipleFragment = getTaskEditMultipleFragment();
      return applyModifications( taskEditMultipleFragment.onFinishEditing() );
   }
   
   
   
   private boolean cancelChanges()
   {
      final TaskEditMultipleFragment taskEditMultipleFragment = getTaskEditMultipleFragment();
      
      boolean finish = true;
      if ( taskEditMultipleFragment.hasChanges() )
      {
         finish = false;
         UIUtils.showCancelWithChangesDialog( this );
      }
      else
      {
         taskEditMultipleFragment.onCancelEditing();
      }
      
      return finish;
   }
   
   
   
   @Override
   protected void handleCancelWithChangesDialogClick( String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         final TaskEditMultipleFragment taskEditMultipleFragment = getTaskEditMultipleFragment();
         taskEditMultipleFragment.onCancelEditing();
         
         finish();
      }
   }
   
   
   
   private void createTaskEditMultipleFragment( Bundle fragmentConfig )
   {
      final Fragment fragment = TaskEditMultipleFragment.newInstance( fragmentConfig );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      transaction.add( R.id.frag_task_edit_multiple, fragment, null );
      
      transaction.commit();
   }
   
   
   
   private TaskEditMultipleFragment getTaskEditMultipleFragment()
   {
      return (TaskEditMultipleFragment) getSupportFragmentManager().findFragmentById( R.id.frag_task_edit_multiple );
   }
   
   
   
   private List< Task > getConfiguredTasksFromIntentConfigAssertNotNull()
   {
      final List< Task > tasks = getIntent().getExtras()
                                            .getParcelableArrayList( Intents.Extras.KEY_TASKS );
      if ( tasks == null )
         throw new AssertionError( "expected tasks to be not null" );
      
      return tasks;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_task_edit_multiple };
   }
}
