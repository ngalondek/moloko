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

package dev.drsoran.moloko.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.RtmServiceConstants;
import dev.drsoran.moloko.app.sync.SyncAdapter;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.content.db.RtmTaskSeriesTable;
import dev.drsoran.moloko.content.db.RtmTasksProviderPart;
import dev.drsoran.moloko.sync.elements.InSyncTask;
import dev.drsoran.moloko.sync.elements.OutSyncTask;
import dev.drsoran.moloko.sync.elements.ServerSyncRtmTaskList;
import dev.drsoran.moloko.sync.elements.SyncRtmTaskList;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public final class RtmTasksSync
{
   private final static Class< RtmTasksSync > TAG = RtmTasksSync.class;
   
   
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
         final List< RtmTaskSeries > tasks = RtmTaskSeriesTable.getLocalCreatedTaskSerieses( provider );
         
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
            MolokoApp.Log.e( TAG, "Getting new created local tasks failed." );
         }
      }
      
      SyncRtmTaskList local_SyncTaskList = null;
      {
         final RtmTasks tasks = RtmTaskSeriesTable.getAllTaskSeries( provider );
         
         if ( tasks == null )
         {
            syncResult.androidSyncResult.databaseError = true;
            MolokoApp.Log.e( TAG, "Getting local tasks failed." );
            return false;
         }
         else
            local_SyncTaskList = new SyncRtmTaskList( tasks );
      }
      
      final ServerSyncRtmTaskList server_SyncTaskList = getServerTasksList( service,
                                                                            lastSync,
                                                                            syncResult );
      if ( server_SyncTaskList == null )
      {
         return false;
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
            MolokoApp.Log.e( TAG, "Retrieving modifications failed", e );
            modifications = new ModificationSet();
         }
         
         boolean doOutSync = modifications.size() > 0;
         
         int numDeleted = 0;
         
         if ( !doOutSync )
         {
            numDeleted = RtmTasksProviderPart.getDeletedTasksCount( provider );
            doOutSync = numDeleted > 0;
         }
         
         if ( doOutSync )
         {
            MolokoApp.Log.d( TAG, "Retrieved " + modifications.size()
               + " modification(s) and " + numDeleted + " deletion(s)" );
            
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
         final ContentProviderSyncableList< InSyncTask > local_SyncList = new ContentProviderSyncableList< InSyncTask >( local_SyncTaskList.getInSyncTasks(),
                                                                                                                         InSyncTask.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_SyncTaskList.getInSyncTasks(),
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
         
         // Process the taskserieses with only deleted tasks at last. Otherwise we would remove local taskserieses and
         // insert them again during inDiff
         final List< IContentProviderSyncOperation > removeDeletedTasksOps = server_SyncTaskList.removeDeletedTasks();
         syncResult.localOps.addAll( removeDeletedTasksOps );
      }
      
      // Sync notes
      return RtmNotesSync.computeSync( service,
                                       provider,
                                       timeLineFactory,
                                       local_SyncTaskList,
                                       server_SyncTaskList,
                                       lastSync,
                                       syncResult );
   }
   
   
   
   public static ServerSyncRtmTaskList getServerTasksList( Service service,
                                                           Date lastSync,
                                                           MolokoSyncResult syncResult )
   {
      ServerSyncRtmTaskList server_SyncTaskList = null;
      {
         try
         {
            final RtmTasks tasks = service.tasks_getList( null, null, lastSync );
            server_SyncTaskList = new ServerSyncRtmTaskList( tasks );
         }
         catch ( ServiceException e )
         {
            MolokoApp.Log.e( TAG, "Getting server lists failed.", e );
            handleResponseCode( syncResult, e );
            return null;
         }
      }
      return server_SyncTaskList;
   }
   
   
   
   private static void sendNewTasks( Service service,
                                     RtmProvider provider,
                                     TimeLineFactory timeLineFactory,
                                     List< RtmTaskSeries > localTaskSerieses,
                                     MolokoSyncResult syncResult )
   {
      if ( localTaskSerieses.size() > 0 )
      {
         MolokoApp.Log.d( TAG, "Sending " + localTaskSerieses.size()
            + " new task(s)" );
         
         RtmTimeline timeline = null;
         
         try
         {
            timeline = timeLineFactory.createTimeline();
         }
         catch ( ServiceException e )
         {
            MolokoApp.Log.e( TAG, "Creating new time line failed", e );
            handleResponseCode( syncResult, e );
            return;
         }
         
         for ( RtmTaskSeries localTaskSeries : localTaskSerieses )
         {
            for ( RtmTask localTask : localTaskSeries.getTasks() )
            {
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
            
            localOutSyncTask.handleAfterServerInsert( serverOutSyncTask )
                            .getBatch( operations );
            
            // Put all differences between local task and new server task as modification in the DB.
            // So, in case of a sync interruption, we still have the changes for the next time.
            localOutSyncTask.computeServerInsertModification( serverOutSyncTask )
                            .getBatch( operations );
            
            final TransactionalAccess transactionalAccess = provider.newTransactionalAccess();
            try
            {
               transactionalAccess.beginTransaction();
               
               provider.applyBatch( operations );
               
               transactionalAccess.setTransactionSuccessful();
            }
            catch ( Throwable e )
            {
               MolokoApp.Log.e( TAG,
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
               MolokoApp.Log.e( TAG, "Applying server operation failed", e );
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
         MolokoApp.Log.e( TAG, "Executing server operation failed", e );
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
