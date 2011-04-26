/*
 * Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.sync.elements.InSyncRtmTaskSeries;
import dev.drsoran.moloko.sync.elements.OutSyncTask;
import dev.drsoran.moloko.sync.elements.SyncRtmTaskList;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public final class RtmTasksSync
{
   private final static String TAG = "Moloko."
      + RtmTasksSync.class.getSimpleName();
   
   
   private final static class AddTaskResult
   {
      public final int responseCode;
      
      public final RtmTaskList taskList;
      
      

      public AddTaskResult( int responseCode, RtmTaskList taskList )
      {
         this.responseCode = responseCode;
         this.taskList = taskList;
      }
   }
   
   

   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      TimeLineFactory timeLineFactory,
                                      Date lastSync,
                                      MolokoSyncResult syncResult )
   {
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
         final List< RtmTaskSeries > tasks = RtmTaskSeriesProviderPart.getLocalCreatedTaskSeries( provider );
         
         if ( tasks != null )
         {
            // Send new tasks
            sendNewTasks( service,
                          (RtmProvider) provider.getLocalContentProvider(),
                          timeLineFactory,
                          tasks,
                          syncResult );
         }
         else
         {
            syncResult.androidSyncResult.databaseError = true;
            Log.e( TAG, "Getting new created local tasks failed." );
         }
      }
      
      SyncRtmTaskList local_SyncTaskList = null;
      {
         final RtmTasks tasks = RtmTaskSeriesProviderPart.getAllTaskSeries( provider );
         
         if ( tasks == null )
         {
            syncResult.androidSyncResult.databaseError = true;
            Log.e( TAG, "Getting local tasks failed." );
            return false;
         }
         else
            local_SyncTaskList = new SyncRtmTaskList( tasks );
      }
      
      SyncRtmTaskList server_SyncTaskList = null;
      {
         try
         {
            final RtmTasks tasks = service.tasks_getList( null, null, lastSync );
            server_SyncTaskList = new SyncRtmTaskList( tasks );
         }
         catch ( ServiceException e )
         {
            Log.e( TAG, "Getting server lists failed.", e );
            handleResponseCode( syncResult, e );
            return false;
         }
      }
      
      if ( timeLineFactory != null )
      {
         ModificationSet modifications;
         try
         {
            modifications = SyncAdapter.getModificationsFor( syncResult.context,
                                                             TaskSeries.CONTENT_URI,
                                                             RawTasks.CONTENT_URI );
         }
         catch ( Throwable e )
         {
            Log.e( TAG, "Retrieving modifications failed", e );
            modifications = new ModificationSet();
         }
         
         boolean doOutSync = modifications.size() > 0;
         
         if ( !doOutSync )
         {
            final int numDeleted = RtmTasksProviderPart.getDeletedTasksCount( provider );
            doOutSync = numDeleted > 0;
         }
         
         if ( doOutSync )
         {
            Log.i( TAG, "Retrieved " + modifications.size()
               + " modification(s)" );
            
            final List< OutSyncTask > server_Tasks = server_SyncTaskList.getOutSyncTasks();
            final List< OutSyncTask > local_Tasks = local_SyncTaskList.getOutSyncTasks();
            
            RtmTimeline timeline = null;
            
            try
            {
               timeline = timeLineFactory.createTimeline();
               
               // Collect all outgoing changes
               final List< IServerSyncOperation< RtmTaskList > > serverOps = SyncDiffer.outDiff( server_Tasks,
                                                                                                 local_Tasks,
                                                                                                 OutSyncTask.LESS_ID,
                                                                                                 modifications,
                                                                                                 timeline,
                                                                                                 lastSync );
               // Send our local changes to the server and update the server list of
               // TaskSeries with the new elements retrieved from server during
               // the commit.
               applyServerOperations( (RtmProvider) provider.getLocalContentProvider(),
                                      serverOps,
                                      server_SyncTaskList );
            }
            catch ( ServiceException e )
            {
               handleResponseCode( syncResult, e );
               return false;
            }
         }
      }
      
      // . <-- At this point we have an up-to-date list of server TaskSeries
      // containing all changes made by outgoing sync.
      {
         final ContentProviderSyncableList< InSyncRtmTaskSeries > local_SyncList = new ContentProviderSyncableList< InSyncRtmTaskSeries >( local_SyncTaskList.getInSyncTasksSeries(),
                                                                                                                                           InSyncRtmTaskSeries.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_SyncTaskList.getInSyncTasksSeries(),
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
      }
      
      // Sync notes
      return RtmNotesSync.computeSync( service,
                                       provider,
                                       timeLineFactory,
                                       server_SyncTaskList,
                                       local_SyncTaskList,
                                       lastSync,
                                       syncResult );
   }
   


   private static void sendNewTasks( Service service,
                                     RtmProvider provider,
                                     TimeLineFactory timeLineFactory,
                                     List< RtmTaskSeries > localTaskSerieses,
                                     MolokoSyncResult syncResult )
   {
      if ( localTaskSerieses.size() > 0 )
      {
         Log.i( TAG, "Sending " + localTaskSerieses.size() + " new task(s)" );
         
         RtmTimeline timeline = null;
         
         try
         {
            timeline = timeLineFactory.createTimeline();
         }
         catch ( ServiceException e )
         {
            Log.e( TAG, "Creating new time line failed", e );
            handleResponseCode( syncResult, e );
            return;
         }
         
         for ( RtmTaskSeries localTaskSeries : localTaskSerieses )
         {
            for ( RtmTask localTask : localTaskSeries.getTasks() )
            {
               // Do not send deleted tasks which have not yet been synced.
               // These tasks were created and deleted only locally.
               if ( localTask.getDeletedDate() == null )
                  sendTask( service,
                            provider,
                            timeline,
                            localTaskSeries,
                            localTask,
                            syncResult );
            }
         }
      }
   }
   


   private final static RtmTaskList sendTask( Service service,
                                              RtmProvider provider,
                                              RtmTimeline timeline,
                                              RtmTaskSeries localTaskSeries,
                                              RtmTask localTask,
                                              MolokoSyncResult syncResult )
   {
      AddTaskResult res = null;
      
      // Create a new task on RTM side with List + Name
      res = addTask( timeline,
                     localTaskSeries.getName(),
                     localTaskSeries.getListId() );
      
      // If our list ID is not accepted, we try again w/o a list ID. So the task will
      // be added to the InBox. This can happen if our local list does not
      // exists anymore on server side.
      if ( res.responseCode == RtmServiceConstants.RtmErrorCodes.LIST_INVALID_ID )
         res = addTask( timeline, localTaskSeries.getName(), null );
      
      if ( res.responseCode == RtmServiceConstants.RtmErrorCodes.NO_ERROR
         && res.taskList != null )
      {
         // addTask() ensures size == 1
         final RtmTaskSeries newServerTaskSeries = res.taskList.getSeries()
                                                               .get( 0 );
         
         final OutSyncTask localOutSyncTask = new OutSyncTask( localTaskSeries );
         final OutSyncTask serverOutSyncTask = new OutSyncTask( newServerTaskSeries );
         
         {
            final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
            
            // Source. Mark the task as added on server side by removing the "new task flag".
            operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          localTaskSeries.getId() ) )
                                                    .withValue( TaskSeries.SOURCE,
                                                                Strings.EMPTY_STRING )
                                                    .build() );
            
            // Change the ID of the local taskseries to the ID of the server taskseries. Referencing entities will also
            // be changed by a DB trigger.
            operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          localTaskSeries.getId() ) )
                                                    .withValue( TaskSeries._ID,
                                                                newServerTaskSeries.getId() )
                                                    .build() );
            
            // Change the ID of the local rawtask to the ID of the server rawtask.
            operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          localTask.getId() ) )
                                                    .withValue( RawTasks._ID,
                                                                serverOutSyncTask.getTask()
                                                                                 .getId() )
                                                    .build() );
            
            // Put all differences between local task and new server task as modification in the DB.
            // So, in case of a sync interruption, we still have the changes for the next time.
            final IContentProviderSyncOperation modifications = localOutSyncTask.computeServerInsertModification( serverOutSyncTask );
            modifications.getBatch( operations );
            
            final TransactionalAccess transactionalAccess = provider.newTransactionalAccess();
            try
            {
               transactionalAccess.beginTransaction();
               
               provider.applyBatch( operations );
               
               transactionalAccess.setTransactionSuccessful();
            }
            catch ( Throwable e )
            {
               Log.e( TAG,
                      "Applying local changes after sending new task failed",
                      e );
               // TODO: Remove task on RTM side in this case. Otherwise the task
               // would get added again on next sync since the SOURCE is still tagged
               // as new.
            }
            finally
            {
               transactionalAccess.endTransaction();
            }
         }
      }
      
      return res.taskList;
   }
   


   private final static void applyServerOperations( RtmProvider rtmProvider /* for deleting modifications */,
                                                    List< ? extends IServerSyncOperation< RtmTaskList > > serverOps,
                                                    SyncRtmTaskList serverList ) throws ServiceException
   {
      for ( IServerSyncOperation< RtmTaskList > serverOp : serverOps )
      {
         if ( !( serverOp instanceof INoopSyncOperation ) )
         {
            try
            {
               final RtmTaskList result = serverOp.execute( rtmProvider );
               
               if ( result != null )
                  // If the set already contains an element in respect to the Comparator,
                  // then we update it by the new received.
                  serverList.update( result );
            }
            catch ( ServiceException e )
            {
               Log.e( TAG, "Applying server operation failed", e );
               throw e;
            }
         }
      }
   }
   


   private final static AddTaskResult addTask( RtmTimeline timeline,
                                               String name,
                                               String listId )
   {
      RtmTaskList taskList = null;
      int respCode = RtmServiceConstants.RtmErrorCodes.NO_ERROR;
      
      try
      {
         final TimeLineResult< RtmTaskList > res = timeline.tasks_add( listId,
                                                                       name )
                                                           .call();
         
         if ( res == null )
            throw new ServiceException( -1,
                                        "ServerOperation produced no result" );
         
         if ( res.element.getSeries().size() == 1 )
            taskList = res.element;
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Executing server operation failed", e );
         respCode = e.responseCode;
      }
      
      return new AddTaskResult( respCode, taskList );
   }
   


   private final static void handleResponseCode( MolokoSyncResult syncResult,
                                                 ServiceException e )
   {
      switch ( e.responseCode )
      {
         case RtmServiceConstants.RtmErrorCodes.LOGIN_FAILED:
         case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
            ++syncResult.androidSyncResult.stats.numAuthExceptions;
            break;
         case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
            ++syncResult.androidSyncResult.stats.numIoExceptions;
            break;
         default :
            if ( e instanceof ServiceInternalException )
               SyncUtils.handleServiceInternalException( (ServiceInternalException) e,
                                                         TAG,
                                                         syncResult.androidSyncResult );
            else
               ++syncResult.androidSyncResult.stats.numParseExceptions;
            break;
      }
   }
   
}
