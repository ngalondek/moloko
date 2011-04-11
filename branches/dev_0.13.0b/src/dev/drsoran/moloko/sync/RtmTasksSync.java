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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentProviderClient;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.TimeLineResult.Transaction;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
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
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public final class RtmTasksSync
{
   private final static String TAG = "Moloko."
      + RtmTasksSync.class.getSimpleName();
   
   

   public static void sendNewTasks( Service service,
                                    RtmProvider provider,
                                    TimeLineFactory timeLineFactory,
                                    SyncRtmTaskList localTasks,
                                    SyncRtmTaskList serverTasks,
                                    MolokoSyncResult syncResult )
   {
      List< OutSyncTask > newOutSyncTasks = new LinkedList< OutSyncTask >();
      
      for ( OutSyncTask outSyncTask : localTasks.getOutSyncTasks() )
      {
         if ( outSyncTask.isNew() )
            newOutSyncTasks.add( outSyncTask );
      }
      
      if ( newOutSyncTasks.size() > 0 )
      {
         Log.i( TAG, "Sending " + newOutSyncTasks.size() + " new task(s)" );
         
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
         
         for ( OutSyncTask newOutSyncTask : newOutSyncTasks )
         {
            final RtmTaskList newTask = sendTask( service,
                                                  provider,
                                                  timeline,
                                                  newOutSyncTask,
                                                  syncResult );
            if ( newTask != null )
            {
               // Put the new added task with the new IDs and set values back to the server list.
               serverTasks.update( newTask );
               
               localTasks.remove( newOutSyncTask.getTaskSeries() );
               
               provider.delete( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                          newOutSyncTask.getTask()
                                                                        .getId() ),
                                null,
                                null );
            }
         }
      }
   }
   


   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      TimeLineFactory timeLineFactory,
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
         // Send new tasks
         sendNewTasks( service,
                       (RtmProvider) provider.getLocalContentProvider(),
                       timeLineFactory,
                       local_SyncTaskList,
                       server_SyncTaskList,
                       syncResult );
         
         // IMPORTANT: Retrieve all modifications after sending tasks cause sending tasks may have altered modifications
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
            modifications = null;
         }
         
         /**
          * Even if modifications are null, we must not return here. We need to apply changes made by adding a new task
          * below.
          **/
         
         // Check if we have outgoing changes
         if ( modifications != null && modifications.size() > 0 )
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
   


   private final static RtmTaskList sendTask( Service service,
                                              RtmProvider provider,
                                              RtmTimeline timeline,
                                              OutSyncTask localOutSyncTask,
                                              MolokoSyncResult syncResult )
   {
      final RtmTaskList[] resultList = new RtmTaskList[ 1 ];
      final Transaction[] transaction = new Transaction[ 1 ];
      
      // Create a new task on RTM side with List + Name
      int respcode = addTask( timeline,
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
      
      if ( resultList[ 0 ] != null && resultList[ 0 ].getSeries().size() > 0 )
      {
         final RtmTaskSeries newServerTaskSeries = resultList[ 0 ].getSeries()
                                                                  .get( 0 );
         
         // Put all differences between local task and new server task as modification in the DB.
         // So, in case of an sync interruption, we still have the changes for the next time.
         {
            final IContentProviderSyncOperation modifications = localOutSyncTask.toInSyncRtmTaskSeries()
                                                                                .computeAfterServerInsertOperation( new InSyncRtmTaskSeries( newServerTaskSeries ) );
            modifications.applyTransactional( provider );
         }
         
         // Read the modifications
         {
            final ModificationSet modifications = SyncAdapter.getModificationsFor( syncResult.context,
                                                                                   Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                             newServerTaskSeries.getId() ),
                                                                                   Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                             newServerTaskSeries.getTasks()
                                                                                                                                .get( 0 )
                                                                                                                                .getId() ) );
            if ( modifications != null && modifications.size() > 0 )
            {
               final IServerSyncOperation< RtmTaskList > serverOp = new OutSyncTask( newServerTaskSeries ).computeServerUpdateOperation( timeline,
                                                                                                                                         modifications,
                                                                                                                                         null /*
                                                                                                                                               * Force
                                                                                                                                               * outgoing
                                                                                                                                               * changes
                                                                                                                                               */);
               try
               {
                  final RtmTaskList list = serverOp.execute( provider );
                  if ( list != null )
                     return list;
               }
               catch ( ServiceException e )
               {
                  Log.e( TAG, "Updating server task after insert failed", e );
                  return null;
               }
            }
         }
         
         return resultList[ 0 ];
      }
      else
         return null;
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
