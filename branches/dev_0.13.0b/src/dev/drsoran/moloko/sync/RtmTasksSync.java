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

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.TimeLineResult.Transaction;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
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
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public final class RtmTasksSync
{
   private final static String TAG = "Moloko."
      + RtmTasksSync.class.getSimpleName();
   
   
   private final static class CmpGreaterCreatedDate implements
            Comparator< OutSyncTask >
   {
      public int compare( OutSyncTask object1, OutSyncTask object2 )
      {
         return object2.getCreatedDate().compareTo( object1.getCreatedDate() );
      }
   }
   
   

   public static boolean sendNewTasks( Service service,
                                       ContentProviderClient provider,
                                       TimeLineFactory timeLineFactory,
                                       SyncRtmTaskList localTasks,
                                       SyncRtmTaskList serverTasks,
                                       Date lastSync,
                                       MolokoSyncResult syncResult )
   {
      List< OutSyncTask > localOutSyncTasks = null;
      
      {
         final List< OutSyncTask > sortedOutSyncTasks = localTasks.getOutSyncTasks( new CmpGreaterCreatedDate() );
         
         // Find the end of the new added tasks
         int endOfNewTasksIdx = 0;
         for ( OutSyncTask outSyncTask : sortedOutSyncTasks )
         {
            if ( outSyncTask.getCreatedDate().after( lastSync ) )
               ++endOfNewTasksIdx;
            else
               break;
         }
         
         localOutSyncTasks = sortedOutSyncTasks.subList( 0,
                                                         endOfNewTasksIdx + 1 );
      }
      
      if ( localOutSyncTasks.size() > 0 )
      {
         Log.i( TAG, "Sending " + localOutSyncTasks.size() + " new task(s)" );
         
         RtmTimeline timeline = null;
         
         try
         {
            timeline = timeLineFactory.createTimeline();
         }
         catch ( ServiceException e )
         {
            Log.e( TAG, "Creating new time line failed", e );
            handleResponseCode( syncResult, e );
            return false;
         }
         
         for ( OutSyncTask localOutSyncTask : localOutSyncTasks )
         {
            if ( sendTask( service,
                           (RtmProvider) provider.getLocalContentProvider(),
                           timeline,
                           localOutSyncTask,
                           serverTasks,
                           syncResult ) )
            {
               // Remove the sent task from the local list of tasks
               localTasks.remove( localOutSyncTask.getTaskSeries() );
               
               // TODO: Remove the local task from the DB since it will
               // be replaced by the RTM version
            }
         }
      }
      
      return true;
   }
   


   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      TimeLineFactory timeLineFactory,
                                      ModificationSet modifications,
                                      Date lastSync,
                                      MolokoSyncResult syncResult )
   {
      
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
      
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
         if ( lastSync != null )
         {
            // Send new tasks
            if ( !sendNewTasks( service,
                                provider,
                                timeLineFactory,
                                server_SyncTaskList,
                                local_SyncTaskList,
                                lastSync,
                                syncResult ) )
               return false;
         }
         
         // Check if we have outgoing changes
         if ( modifications.hasModifications( TaskSeries.CONTENT_URI )
            || modifications.hasModifications( RawTasks.CONTENT_URI ) )
         {
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
      
      // At this point we have an up-to-date list of server TaskSeries
      // containing all changes made by outgoing sync.
      {
         final ContentProviderSyncableList< InSyncRtmTaskSeries > local_SyncList = new ContentProviderSyncableList< InSyncRtmTaskSeries >( local_SyncTaskList.getInSyncTasksSeries(),
                                                                                                                                           InSyncRtmTaskSeries.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_SyncTaskList.getInSyncTasksSeries(),
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
      }
      
      return true;
   }
   


   private final static boolean sendTask( Service service,
                                          RtmProvider rtmProvider,
                                          RtmTimeline timeline,
                                          OutSyncTask localOutSyncTask,
                                          SyncRtmTaskList serverTasks,
                                          MolokoSyncResult syncResult )
   {
      boolean ok = true;
      
      RtmTaskList[] resultList = new RtmTaskList[ 1 ];
      Transaction[] transaction = new Transaction[ 1 ];
      
      int respcode = RtmServiceConstants.RtmErrorCodes.LIST_INVALID_ID;
      // Create a new task on RTM side with List + Name
      {
         respcode = addTask( timeline,
                             localOutSyncTask.getTaskSeries().getName(),
                             localOutSyncTask.getTaskSeries().getListId(),
                             resultList,
                             transaction );
         
         // If our list ID is not accepted, we try again w/o a list ID. So the task will
         // be added to the InBox. This can happen if our local list does not
         // exists anymore on server side.
         if ( respcode == RtmServiceConstants.RtmErrorCodes.LIST_INVALID_ID )
            respcode = addTask( timeline,
                                localOutSyncTask.getTaskSeries().getName(),
                                null,
                                resultList,
                                transaction );
      }
      
      ok = respcode == RtmServiceConstants.RtmErrorCodes.NO_ERROR;
      
      if ( ok && resultList[ 0 ] != null
         && resultList[ 0 ].getSeries().size() > 0 )
      {
         /** Now we should have the new task IDs from RTM. **/
         
         // This has the IDs
         final OutSyncTask serverTaskSeries = new OutSyncTask( resultList[ 0 ].getSeries()
                                                                              .get( 0 ) );
         
         // Update the task with the new IDs with the local values
         final IServerSyncOperation< RtmTaskList > operation = localOutSyncTask.computeServerAfterInsertOperation( timeline,
                                                                                                                   serverTaskSeries );
         try
         {
            resultList[ 0 ] = operation.execute( rtmProvider );
            
            // Put the new added task with the new IDs and set values back to the server list.
            serverTasks.update( resultList[ 0 ] );
         }
         catch ( ServiceException e )
         {
            ok = false;
            
            Log.e( TAG,
                   "Applying server operation failed. Reverting changes",
                   e );
            
            // If something failed, we undo all changes made so far
            operation.revert( service );
            
            if ( transaction[ 0 ] != null && transaction[ 0 ].undoable )
            {
               try
               {
                  service.transactions_undo( transaction[ 0 ].timelineId,
                                             transaction[ 0 ].transactionId );
               }
               catch ( ServiceException te )
               {
                  Log.e( TAG, "Undo of transaction failed! Help us god!", te );
               }
            }
         }
      }
      
      return ok;
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
               
               if ( result == null )
                  throw new ServiceException( -1,
                                              "ServerSyncOperation produced no result" );
               
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
   


   private final static int addTask( RtmTimeline timeline,
                                     String name,
                                     String listId,
                                     RtmTaskList[] resultList,
                                     Transaction[] transaction )
   {
      try
      {
         final TimeLineResult< RtmTaskList > res = timeline.tasks_add( listId,
                                                                       name )
                                                           .call();
         
         if ( res == null )
            throw new ServiceException( -1,
                                        "ServerOperation produced no result" );
         
         transaction[ 0 ] = res.transaction;
         resultList[ 0 ] = res.element;
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Executing server operation failed", e );
         return e.responseCode;
      }
      
      return RtmServiceConstants.RtmErrorCodes.NO_ERROR;
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
