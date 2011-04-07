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
import java.util.List;

import android.content.ContentProviderClient;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
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
            
            return false;
         }
      }
      
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
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
                                                                                                 OutSyncTask.LESS_RTM_ID,
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
               return false;
            }
         }
      }
      
      // At this point we have an up-to-date list of server TaskSeries
      // containing all changes made by outgoing sync.
      {
         final ContentProviderSyncableList< InSyncRtmTaskSeries > local_SyncList = new ContentProviderSyncableList< InSyncRtmTaskSeries >( local_SyncTaskList.getInSyncTasksSeries(),
                                                                                                                                           InSyncRtmTaskSeries.LESS_RTM_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_SyncTaskList.getInSyncTasksSeries(),
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
      }
      
      return true;
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
}
