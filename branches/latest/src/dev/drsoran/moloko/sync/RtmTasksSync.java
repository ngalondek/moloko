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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentProviderClient;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.SyncTask;


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
      final List< RtmTaskSeries > local_TaskSeries = toPlainList( RtmTaskSeriesProviderPart.getAllTaskSeries( provider ) );
      
      if ( local_TaskSeries == null )
      {
         syncResult.androidSyncResult.databaseError = true;
         Log.e( TAG, "Getting local tasks failed." );
         return false;
      }
      
      List< RtmTaskSeries > server_TaskSeries;
      
      try
      {
         server_TaskSeries = toPlainList( service.tasks_getList( null,
                                                                 null,
                                                                 lastSync ) );
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
      
      Collections.sort( server_TaskSeries, RtmTaskSeries.LESS_ID );
      Collections.sort( local_TaskSeries, RtmTaskSeries.LESS_ID );
      
      final List< SyncTask > local_Tasks = toPlainList( local_TaskSeries );
      Collections.sort( local_Tasks, SyncTask.LESS_ID );
      
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
         // Check if we have outgoing changes
         if ( modifications.hasModifications( TaskSeries.CONTENT_URI )
            || modifications.hasModifications( RawTasks.CONTENT_URI ) )
         {
            final List< SyncTask > server_Tasks = toPlainList( server_TaskSeries );
            Collections.sort( server_Tasks, SyncTask.LESS_ID );
            
            RtmTimeline timeline = null;
            
            try
            {
               timeline = timeLineFactory.createTimeline();
               
               // Collect all outgoing changes
               final List< IServerSyncOperation< RtmTaskList > > serverOps = SyncDiffer.outDiff( server_Tasks,
                                                                                                 local_Tasks,
                                                                                                 SyncTask.LESS_ID,
                                                                                                 modifications,
                                                                                                 timeline,
                                                                                                 lastSync );
               // Send our local changes to the server and update the server list of
               // TaskSeries with the new elements retrieved from server during
               // the commit.
               applyServerOperations( (RtmProvider) provider.getLocalContentProvider(),
                                      serverOps,
                                      server_TaskSeries,
                                      RtmTaskSeries.LESS_ID );
            }
            catch ( ServiceException e )
            {
               return false;
            }
         }
      }
      
      // At this point we have an up-to-date list of server TaskSeries
      // containing all changes made by outgoing sync.
      
      // Sync TaskSeries incoming cause we need to insert new TaskSeries only once.
      // This will also sync Notes, Tags, Participant cause this is shared between all SyncTasks
      {
         final ContentProviderSyncableList< RtmTaskSeries > local_SyncList = new ContentProviderSyncableList< RtmTaskSeries >( local_TaskSeries,
                                                                                                                               RtmTaskSeries.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_TaskSeries,
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
      }
      
      // Sync SyncTasks incoming
      {
         // Recreate server SyncTasks cause this list may have updated due to outgoing sync operations.
         final List< SyncTask > server_Tasks = toPlainList( server_TaskSeries );
         Collections.sort( server_Tasks, SyncTask.LESS_ID );
         
         final ContentProviderSyncableList< SyncTask > local_SyncList = new ContentProviderSyncableList< SyncTask >( local_Tasks,
                                                                                                                     SyncTask.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_Tasks,
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
      }
      
      return true;
   }
   


   private final static List< SyncTask > toPlainList( List< RtmTaskSeries > taskSeries )
   {
      if ( taskSeries == null )
         return null;
      
      final List< SyncTask > result = new LinkedList< SyncTask >();
      
      for ( RtmTaskSeries series : taskSeries )
         for ( RtmTask task : series.getTasks() )
            result.add( new SyncTask( series, task ) );
      
      return new ArrayList< SyncTask >( result );
   }
   


   private final static List< RtmTaskSeries > toPlainList( RtmTasks tasks )
   {
      if ( tasks == null )
         return null;
      
      final List< RtmTaskSeries > result = new LinkedList< RtmTaskSeries >();
      
      for ( RtmTaskList rtmTaskList : tasks.getLists() )
         for ( RtmTaskSeries series : rtmTaskList.getSeries() )
            result.add( series );
      
      return new ArrayList< RtmTaskSeries >( result );
   }
   


   private final static void applyServerOperations( RtmProvider rtmProvider,
                                                    List< ? extends IServerSyncOperation< RtmTaskList > > serverOps,
                                                    List< RtmTaskSeries > sortedServerElements,
                                                    Comparator< ? super RtmTaskSeries > cmp ) throws ServiceException
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
               
               final List< RtmTaskSeries > taskSeries = result.getSeries();
               
               for ( RtmTaskSeries rtmTaskSeries : taskSeries )
               {
                  // If the set already contains an element in respect to the Comparator,
                  // then we update it by the new received.
                  final int pos = Collections.binarySearch( sortedServerElements,
                                                            rtmTaskSeries,
                                                            cmp );
                  
                  if ( pos >= 0 )
                  {
                     sortedServerElements.remove( pos );
                     sortedServerElements.add( pos, rtmTaskSeries );
                  }
                  else
                  {
                     sortedServerElements.add( ( -pos - 1 ), rtmTaskSeries );
                  }
               }
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
