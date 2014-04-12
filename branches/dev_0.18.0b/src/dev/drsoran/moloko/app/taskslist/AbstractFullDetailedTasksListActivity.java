/*
 * Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskslist;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.lists.AddRenameListDialogFragment;
import dev.drsoran.moloko.app.lists.IAddRenameListFragmentListener;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.IEditFragment;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.ui.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.ui.fragments.listeners.IMolokoEditDialogFragmentListener;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements ITasksListActionModeListener,
         IShowTasksWithTagsListener, IQuickAddTaskActionModeListener,
         ILoaderFragmentListener, IMolokoEditDialogFragmentListener,
         IAddRenameListFragmentListener
{
   
   protected AbstractFullDetailedTasksListActivity()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AbstractFullDetailedTasksListActivity.class );
   }
   
   
   
   @Override
   public void onValidateDialogFragment( IEditFragment editDialogFragment )
   {
      validateFragment( editDialogFragment );
   }
   
   
   
   @Override
   public void onFinishEditDialogFragment( IEditFragment editDialogFragment )
   {
      finishFragmentEditing( editDialogFragment );
   }
   
   
   
   @Override
   public void onCancelEditDialogFragment( IEditFragment editDialogFragment )
   {
      // In case of a dialog we cannot show a cancel query since the dialog has already gone.
      editDialogFragment.onCancelEditing();
   }
   
   
   
   @Override
   public void onInsertNewList( TasksList tasksList )
   {
      getAppContext().getContentEditService().insertTasksList( this, tasksList );
   }
   
   
   
   @Override
   public void onRenameList( TasksList tasksList )
   {
      getAppContext().getContentEditService().updateTasksList( this, tasksList );
   }
   
   
   
   @Override
   public void onFragmentLoadStarted( int fragmentId, String fragmentTag )
   {
   }
   
   
   
   @Override
   public void onFragmentLoadFinished( int fragmentId,
                                       String fragmentTag,
                                       boolean success )
   {
      if ( success && !hasShownMultiSelectionNotification()
         && getTasksListFragment().getTaskCount() > 0 )
      {
         executeDelayed( new Runnable()
                         {
                            @Override
                            public void run()
                            {
                               showMultiSelectionNotification();
                            }
                         },
                         getResources().getInteger( R.integer.popup_notification_delay_on_ms ) );
      }
   }
   
   
   
   @Override
   public Fragment createTasksListFragment( Bundle config )
   {
      return FullDetailedTasksListFragment.newInstance( config );
   }
   
   
   
   private boolean hasShownMultiSelectionNotification()
   {
      return getAppContext().getSettings()
                            .hasNotifiedTaskListMultiSelectionHint();
   }
   
   
   
   private void showMultiSelectionNotification()
   {
      new AlertDialogFragment.Builder( View.NO_ID ).setTitle( getString( R.string.phr_hint ) )
                                                   .setMessage( getString( R.string.abstaskslist_notif_multi_selection ) )
                                                   .setNeutralButton( android.R.string.ok )
                                                   .show( this );
      
      getAppContext().getSettings().setTaskListMultiSelectionHintNotified();
   }
   
   
   
   protected void showAddListDialog()
   {
      final Bundle config = new Bundle();
      config.putSerializable( Intents.Extras.KEY_FILTER, getActiveFilter() );
      
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      UiUtils.showDialogFragment( this,
                                  dialogFragment,
                                  String.valueOf( R.id.frag_add_rename_list ) );
   }
   
   
   
   private List< Task > getSelectedTasks()
   {
      return new ArrayList< Task >( ( (AbstractTasksListFragmentAdapter) getTasksListFragment().getListView()
                                                                                               .getAdapter() ).getSelectedItems() );
   }
   
}
