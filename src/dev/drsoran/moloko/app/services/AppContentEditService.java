/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.services;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;
import dev.drsoran.Iterables;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentEditService;
import dev.drsoran.moloko.ui.UiUtils;


public class AppContentEditService implements IAppContentEditService
{
   private final Context context;
   
   private final IContentEditService contentEditService;
   
   private final IAccountService accountService;
   
   
   
   public AppContentEditService( Context context,
      IContentEditService contentEditService, IAccountService accountService )
   {
      this.context = context;
      this.contentEditService = contentEditService;
      this.accountService = accountService;
   }
   
   
   
   @Override
   public void insertTask( final Task task )
   {
      if ( checkWritableAccess() )
      {
         final AppContentEditInfo editInfo = new AppContentEditInfo( context.getString( R.string.toast_insert_task,
                                                                                        task.getName() ),
                                                                     context.getString( R.string.toast_insert_task_ok,
                                                                                        task.getName() ),
                                                                     context.getString( R.string.toast_insert_task_fail,
                                                                                        task.getName() ) );
         
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               contentEditService.insertTask( task );
            }
         }, editInfo );
      }
   }
   
   
   
   @Override
   public void updateTask( Task task )
   {
      updateTasks( Collections.singletonList( task ) );
   }
   
   
   
   @Override
   public void updateTasks( final Collection< ? extends Task > tasks )
   {
      final int tasksCount = tasks.size();
      if ( tasksCount > 0 && checkWritableAccess() )
      {
         final Resources res = context.getResources();
         final AppContentEditInfo editInfo = new AppContentEditInfo( res.getQuantityString( R.plurals.toast_save_task,
                                                                                            tasksCount,
                                                                                            tasksCount ),
                                                                     res.getQuantityString( R.plurals.toast_save_task_ok,
                                                                                            tasksCount,
                                                                                            tasksCount,
                                                                                            Iterables.first( tasks )
                                                                                                     .getName() ),
                                                                     res.getQuantityString( R.plurals.toast_save_task_failed,
                                                                                            tasksCount,
                                                                                            tasksCount ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               for ( Task task : tasks )
               {
                  contentEditService.updateTask( task.getId(), task );
               }
            }
         }, editInfo );
      }
   }
   
   
   
   @Override
   public void deleteTask( Task task )
   {
      deleteTasks( Collections.singletonList( task ) );
   }
   
   
   
   @Override
   public void deleteTasks( final Collection< ? extends Task > tasks )
   {
      final int tasksCount = tasks.size();
      if ( tasksCount > 0 && checkWritableAccess() )
      {
         final Resources res = context.getResources();
         final AppContentEditInfo editInfo = new AppContentEditInfo( res.getQuantityString( R.plurals.toast_delete_task,
                                                                                            tasksCount,
                                                                                            tasksCount ),
                                                                     res.getQuantityString( R.plurals.toast_deleted_task,
                                                                                            tasksCount,
                                                                                            tasksCount,
                                                                                            Iterables.first( tasks )
                                                                                                     .getName() ),
                                                                     res.getQuantityString( R.plurals.toast_delete_task_failed,
                                                                                            tasksCount,
                                                                                            tasksCount ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               for ( Task task : tasks )
               {
                  contentEditService.deleteTask( task.getId() );
               }
            }
         }, editInfo );
      }
   }
   
   
   
   @Override
   public void completeTask( Task task, long completedMillisUtc )
   {
      completeTasks( Collections.singletonList( task ), completedMillisUtc );
   }
   
   
   
   @Override
   public void completeTasks( final Collection< ? extends Task > tasks,
                              final long completedMillisUtc )
   {
      final int tasksCount = tasks.size();
      if ( tasksCount > 0 && checkWritableAccess() )
      {
         final Resources res = context.getResources();
         final AppContentEditInfo editInfo = new AppContentEditInfo( res.getQuantityString( R.plurals.toast_save_task,
                                                                                            tasksCount,
                                                                                            tasksCount ),
                                                                     res.getQuantityString( R.plurals.toast_completed_task,
                                                                                            tasksCount,
                                                                                            tasksCount,
                                                                                            Iterables.first( tasks )
                                                                                                     .getName() ),
                                                                     res.getQuantityString( R.plurals.toast_save_task_failed,
                                                                                            tasksCount,
                                                                                            tasksCount ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               for ( Task task : tasks )
               {
                  task.setCompletedMillisUtc( completedMillisUtc );
                  contentEditService.updateTask( task.getId(), task );
               }
            }
         }, editInfo );
      }
   }
   
   
   
   @Override
   public void incompleteTask( Task task )
   {
      incompleteTasks( Collections.singletonList( task ) );
   }
   
   
   
   @Override
   public void incompleteTasks( final Collection< ? extends Task > tasks )
   {
      completeTasks( tasks, Constants.NO_TIME );
   }
   
   
   
   @Override
   public void postponeTask( Task task )
   {
      postponeTasks( Collections.singletonList( task ) );
   }
   
   
   
   @Override
   public void postponeTasks( final Collection< ? extends Task > tasks )
   {
      final int tasksCount = tasks.size();
      if ( tasksCount > 0 && checkWritableAccess() )
      {
         final Resources res = context.getResources();
         final AppContentEditInfo editInfo = new AppContentEditInfo( res.getQuantityString( R.plurals.toast_save_task,
                                                                                            tasksCount,
                                                                                            tasksCount ),
                                                                     res.getQuantityString( R.plurals.toast_postponed_task,
                                                                                            tasksCount,
                                                                                            tasksCount,
                                                                                            Iterables.first( tasks )
                                                                                                     .getName() ),
                                                                     res.getQuantityString( R.plurals.toast_save_task_failed,
                                                                                            tasksCount,
                                                                                            tasksCount ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               for ( Task task : tasks )
               {
                  task.setPostponedCount( task.getPostponedCount() + 1 );
                  contentEditService.updateTask( task.getId(), task );
               }
            }
         }, editInfo );
      }
   }
   
   
   
   @Override
   public void insertTasksList( final TasksList tasksList )
   {
      if ( checkWritableAccess() )
      {
         final AppContentEditInfo editInfo = new AppContentEditInfo( context.getString( R.string.toast_insert_list,
                                                                                        tasksList.getName() ),
                                                                     context.getString( R.string.toast_insert_list_ok,
                                                                                        tasksList.getName() ),
                                                                     context.getString( R.string.toast_insert_list_fail,
                                                                                        tasksList.getName() ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               contentEditService.insertTasksList( tasksList );
            }
         }, editInfo );
      }
   }
   
   
   
   @Override
   public void updateTasksList( final TasksList tasksList )
   {
      if ( checkWritableAccess() )
      {
         final AppContentEditInfo editInfo = new AppContentEditInfo( context.getString( R.string.toast_save_list,
                                                                                        tasksList.getName() ),
                                                                     context.getString( R.string.toast_save_list_ok,
                                                                                        tasksList.getName() ),
                                                                     context.getString( R.string.toast_save_list_failed,
                                                                                        tasksList.getName() ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               contentEditService.updateTasksList( tasksList.getId(), tasksList );
            }
         },
                           editInfo );
      }
   }
   
   
   
   @Override
   public void deleteTasksList( final TasksList tasksList )
   {
      if ( checkWritableAccess() )
      {
         final AppContentEditInfo editInfo = new AppContentEditInfo( context.getString( R.string.toast_delete_list,
                                                                                        tasksList.getName() ),
                                                                     context.getString( R.string.toast_delete_list_ok,
                                                                                        tasksList.getName() ),
                                                                     context.getString( R.string.toast_delete_list_failed,
                                                                                        tasksList.getName() ) );
         performOperation( new Runnable()
         {
            @Override
            public void run()
            {
               contentEditService.deleteTasksList( tasksList.getId() );
            }
         }, editInfo );
      }
   }
   
   
   
   private boolean checkWritableAccess()
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog();
         return false;
      }
      
      return true;
   }
   
   
   
   private < T > void performOperation( Runnable operation,
                                        AppContentEditInfo editInfo )
   {
      try
      {
         operation.run();
         showEditSucceededAsToast( editInfo );
      }
      catch ( NoSuchElementException e )
      {
         showEditFailedAsToast( editInfo );
      }
      catch ( ContentException e )
      {
         showEditFailedAsToast( editInfo );
      }
   }
   
   
   
   private void showEditSucceededAsToast( AppContentEditInfo editInfo )
   {
      Toast.makeText( context, editInfo.getSuccessMessage(), Toast.LENGTH_SHORT )
           .show();
   }
   
   
   
   private void showEditFailedAsToast( AppContentEditInfo editInfo )
   {
      Toast.makeText( context, editInfo.getFailedMessage(), Toast.LENGTH_LONG )
           .show();
   }
   
   
   
   private boolean hasWritableAccess()
   {
      return accountService.isWriteableAccess( accountService.getRtmAccount() );
   }
   
   
   
   private void showOnlyReadableDatabaseAccessDialog()
   {
      UiUtils.showReadOnlyAccessDialog( context );
   }
}
