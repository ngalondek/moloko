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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.TaskEditMultipleFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class TaskEditMultipleActivity extends MolokoFragmentActivity implements
         ITaskEditFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskEditMultipleActivity.class.getSimpleName();
   
   
   public static class Config extends TaskEditMultipleFragment.Config
   {
   }
   
   
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
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.SAVE,
                                   getString( R.string.app_save ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_disc,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   hasRtmWriteAccess );
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.ABORT,
                                   getString( R.string.phr_cancel_sync ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_cancel,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   hasRtmWriteAccess );
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
   public boolean onFinishTaskEditingByInputMethod()
   {
      if ( finishEditing( FinishEditMode.SAVE ) )
      {
         finish();
         return true;
      }
      
      return false;
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
      return taskEditMultipleFragment.onFinishEditing();
   }
   
   
   
   private boolean cancelChanges()
   {
      final TaskEditMultipleFragment taskEditMultipleFragment = getTaskEditMultipleFragment();
      
      boolean finish = true;
      if ( taskEditMultipleFragment.hasChanges() )
      {
         finish = false;
         
         UIUtils.newCancelWithChangesDialog( this, new Runnable()
         {
            @Override
            public void run()
            {
               taskEditMultipleFragment.onCancelEditing();
               finish();
            }
         }, null ).show();
      }
      else
      {
         taskEditMultipleFragment.onCancelEditing();
      }
      
      return finish;
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
                                            .getParcelableArrayList( Config.TASKS );
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
