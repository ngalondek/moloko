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

package dev.drsoran.moloko.app.content;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

import android.widget.Toast;
import dev.drsoran.moloko.app.services.AppContentEditInfo;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.IAppContentEditService;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentEditService;
import dev.drsoran.moloko.ui.UiUtils;


public class AppContentEditService implements IAppContentEditService
{
   private final IContentEditService contentEditService;
   
   private final IAccountService accountService;
   
   
   
   public AppContentEditService( IContentEditService contentEditService,
      IAccountService accountService )
   {
      this.contentEditService = contentEditService;
      this.accountService = accountService;
   }
   
   
   
   @Override
   public void insertTask( Task task, AppContentEditInfo editInfo )
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog( editInfo );
      }
      else
      {
         try
         {
            contentEditService.insertTask( task );
            showEditSucceededAsToast( editInfo );
         }
         catch ( ContentException e )
         {
            showEditFailedAsToast( editInfo );
         }
      }
   }
   
   
   
   @Override
   public void updateTask( Task task, AppContentEditInfo editInfo )
   {
      updateTasks( Collections.singletonList( task ), editInfo );
   }
   
   
   
   @Override
   public void updateTasks( Collection< ? extends Task > tasks,
                            AppContentEditInfo editInfo )
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog( editInfo );
      }
      else
      {
         try
         {
            for ( Task task : tasks )
            {
               contentEditService.updateTask( task.getId(), task );
            }
            
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
      
   }
   
   
   
   @Override
   public void deleteTask( Task task, AppContentEditInfo editInfo )
   {
      deleteTasks( Collections.singletonList( task ), editInfo );
   }
   
   
   
   @Override
   public void deleteTasks( Collection< ? extends Task > tasks,
                            AppContentEditInfo editInfo )
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog( editInfo );
      }
      else
      {
         try
         {
            for ( Task task : tasks )
            {
               contentEditService.deleteTask( task.getId() );
            }
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
   }
   
   
   
   @Override
   public void insertTasksList( TasksList tasksList, AppContentEditInfo editInfo )
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog( editInfo );
      }
      else
      {
         try
         {
            contentEditService.insertTasksList( tasksList );
            showEditSucceededAsToast( editInfo );
         }
         catch ( ContentException e )
         {
            showEditFailedAsToast( editInfo );
         }
      }
   }
   
   
   
   @Override
   public void updateTasksList( TasksList tasksList, AppContentEditInfo editInfo )
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog( editInfo );
      }
      else
      {
         try
         {
            contentEditService.updateTasksList( tasksList.getId(), tasksList );
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
      
   }
   
   
   
   @Override
   public void deleteTasksList( TasksList tasksList, AppContentEditInfo editInfo )
   {
      if ( !hasWritableAccess() )
      {
         showOnlyReadableDatabaseAccessDialog( editInfo );
      }
      else
      {
         try
         {
            contentEditService.deleteTasksList( tasksList.getId() );
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
      
   }
   
   
   
   private void showEditSucceededAsToast( AppContentEditInfo editInfo )
   {
      Toast.makeText( editInfo.getContext(),
                      editInfo.getSuccessMessage(),
                      Toast.LENGTH_SHORT ).show();
   }
   
   
   
   private void showEditFailedAsToast( AppContentEditInfo editInfo )
   {
      Toast.makeText( editInfo.getContext(),
                      editInfo.getFailedMessage(),
                      Toast.LENGTH_LONG ).show();
   }
   
   
   
   private boolean hasWritableAccess()
   {
      return accountService.isWriteableAccess( accountService.getRtmAccount() );
   }
   
   
   
   private void showOnlyReadableDatabaseAccessDialog( AppContentEditInfo editInfo )
   {
      UiUtils.showReadOnlyAccessDialog( editInfo.getContext() );
   }
}
