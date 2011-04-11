/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.sync.elements;

import java.util.Comparator;
import java.util.Date;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;


public class InSyncRtmTask implements IContentProviderSyncable< InSyncRtmTask >
{
   private final static class LessIdComperator implements
            Comparator< InSyncRtmTask >
   {
      public int compare( InSyncRtmTask object1, InSyncRtmTask object2 )
      {
         return object1.task.getId().compareTo( object2.task.getId() );
      }
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final RtmTask task;
   
   

   public InSyncRtmTask( RtmTask task )
   {
      if ( task == null )
         throw new NullPointerException( "task is null" );
      
      this.task = task;
   }
   


   public Date getDeletedDate()
   {
      return task.getDeletedDate();
   }
   


   @Override
   public String toString()
   {
      return task.toString();
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( RtmTasksProviderPart.insertTask( task ) )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( InSyncRtmTask serverElement )
   {
      ContentProviderSyncOperation.Builder operations = ContentProviderSyncOperation.newUpdate();
      
      {
         final Uri contentUri = Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                          task.getId() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getDue(), task.getDue() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.DUE_DATE,
                                                                MolokoDateUtils.getTime( serverElement.task.getDue() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getHasDueTime(),
                                    task.getHasDueTime() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.HAS_DUE_TIME,
                                                                serverElement.task.getHasDueTime() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getAdded(),
                                    task.getAdded() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.ADDED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.task.getAdded() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getCompleted(),
                                    task.getCompleted() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.COMPLETED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.task.getCompleted() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getPriority(),
                                    task.getPriority() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.PRIORITY,
                                                                RtmTask.convertPriority( serverElement.task.getPriority() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getPostponed(),
                                    task.getPostponed() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.POSTPONED,
                                                                serverElement.task.getPostponed() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getEstimate(),
                                    task.getEstimate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.ESTIMATE,
                                                                serverElement.task.getEstimate() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.task.getEstimateMillis(),
                                    task.getEstimateMillis() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.ESTIMATE_MILLIS,
                                                                serverElement.task.getEstimateMillis() )
                                                    .build() );
      }
      
      return operations.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                                   task.getId() ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeAfterServerInsertOperation( InSyncRtmTask serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      // All differences to the new server element will be added as modification
      final Uri newUri = Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                   serverElement.task.getId() );
      
      // Priority
      if ( SyncUtils.hasChanged( task.getPriority(),
                                 serverElement.task.getPriority() ) )
         operation.add( Modification.newModificationOperation( newUri,
                                                               RawTasks.PRIORITY,
                                                               RtmTask.convertPriority( task.getPriority() ) ) );
      
      // Completed date
      if ( SyncUtils.hasChanged( task.getCompleted(),
                                 serverElement.task.getCompleted() ) )
         operation.add( Modification.newModificationOperation( newUri,
                                                               RawTasks.COMPLETED_DATE,
                                                               MolokoDateUtils.getTime( task.getCompleted() ) ) );
      
      // Due date
      if ( SyncUtils.hasChanged( task.getDue(), serverElement.task.getDue() ) )
         operation.add( Modification.newModificationOperation( newUri,
                                                               RawTasks.DUE_DATE,
                                                               MolokoDateUtils.getTime( task.getDue() ) ) );
      
      // Has due time
      if ( SyncUtils.hasChanged( task.getHasDueTime(),
                                 serverElement.task.getHasDueTime() ) )
         operation.add( Modification.newModificationOperation( newUri,
                                                               RawTasks.HAS_DUE_TIME,
                                                               task.getHasDueTime() ) );
      
      // Estimate
      if ( SyncUtils.hasChanged( task.getEstimate(),
                                 serverElement.task.getEstimate() ) )
         operation.add( Modification.newModificationOperation( newUri,
                                                               RawTasks.ESTIMATE,
                                                               task.getEstimate() ) );
      
      /**
       * Postponed can not be synced. Otherwise we had to store the initial due date on local task creation of the task
       * and set this initial date after creation of the task on RTM side. After this, we could call postpone 1..n
       * times. This is not supported atm.
       **/
      
      return operation.build();
   }
}
