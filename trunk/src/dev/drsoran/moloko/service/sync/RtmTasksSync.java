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

package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.SyncUtils;


public final class RtmTasksSync
{
   private final static String TAG = "Moloko."
      + RtmTasksSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< IContentProviderSyncOperation > operations )
   {
      // Get all lists from local database
      final RtmTasks local_Tasks = RtmTaskSeriesProviderPart.getAllTaskSeries( provider );
      
      if ( local_Tasks == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local tasks failed." );
         return false;
      }
      
      RtmTasks server_Tasks = null;
      
      Date lastSync = null;
      
      // Check if we can shorten the list of tasks to sync
      {
         final ArrayList< Long > lastInAndOut = SyncProviderPart.getLastInAndLastOut( provider );
         
         if ( lastInAndOut != null && lastInAndOut.size() > 1 )
         {
            final Long lastIn = lastInAndOut.get( 0 );
            
            if ( lastIn != null )
               lastSync = new Date( lastIn );
         }
      }
      
      try
      {
         server_Tasks = service.tasks_getList( null, null, lastSync );
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server lists failed.", e );
         
         switch ( e.responseCode )
         {
            case RtmServiceConstants.RtmErrorCodes.LOGIN_FAILED:
            case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
               ++syncResult.stats.numAuthExceptions;
               break;
            case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
               ++syncResult.stats.numIoExceptions;
               break;
            default :
               if ( e instanceof ServiceInternalException )
               {
                  SyncUtils.handleServiceInternalException( (ServiceInternalException) e,
                                                            TAG,
                                                            syncResult );
               }
               else
                  ++syncResult.stats.numParseExceptions;
               break;
         }
         
         return false;
      }
      
      boolean ok = true;
      
      // Sync task lists
      final ContentProviderSyncableList< RtmTaskList > local_SyncList = new ContentProviderSyncableList< RtmTaskList >( provider,
                                                                                                                        local_Tasks.getLists(),
                                                                                                                        RtmTaskList.LESS_ID );
      
      ArrayList< IContentProviderSyncOperation > syncOperations = null;
      
      // Check if we do a full or incremental sync
      if ( lastSync != null )
      {
         syncOperations = diffIncRtmTaskList( provider,
                                              server_Tasks.getLists(),
                                              local_SyncList );
      }
      else
      {
         syncOperations = SyncDiffer.diff( server_Tasks.getLists(),
                                           local_SyncList );
      }
      
      ok = syncOperations != null;
      
      if ( ok )
      {
         operations.addAll( syncOperations );
      }
      
      return ok;
   }
   


   /**
    * This is a special version explicitly for lists of {@link RtmTaskSeries}. This algorithm is for an incremental sync
    * with a last sync time stamp where deleted tasks are explicitly marked as deleted and not simply missing. Hence
    * this case is special for the direction RTM Server -> local this method uses {@link IContentProviderSyncable}
    * operations.
    */
   private final static ArrayList< IContentProviderSyncOperation > diffIncRtmTaskList( ContentProviderClient provider,
                                                                                       List< RtmTaskList > reference,
                                                                                       ContentProviderSyncableList< RtmTaskList > target ) throws NullPointerException
   {
      if ( reference == null || target == null )
         throw new NullPointerException();
      
      boolean ok = true;
      
      ArrayList< IContentProviderSyncOperation > operations = new ArrayList< IContentProviderSyncOperation >();
      
      // for each element of the reference list
      for ( Iterator< RtmTaskList > i = reference.iterator(); ok && i.hasNext(); )
      {
         final RtmTaskList refElement = i.next();
         
         final int pos = target.find( refElement );
         
         // INSERT: The reference element is not contained in the target list.
         if ( pos < 0 )
         {
            // Remove all deleted tasks from the reference list. These tasks
            // we never had locally.
            refElement.removeDeletedTaskSeries();
            
            final IContentProviderSyncOperation taskListOperation = target.computeInsertOperation( refElement );
            
            ok = taskListOperation != null;
            
            if ( ok
               && !( taskListOperation instanceof NoopContentProviderSyncOperation ) )
               operations.add( taskListOperation );
         }
         
         // UPDATE: The reference element is contained in the target list. So check if something has
         // changed in this list. If so we only have the changes.
         else if ( refElement.getSeries().size() > 0 )
         {
            final ArrayList< RtmTaskSeries > refTaskSeriesList = new ArrayList< RtmTaskSeries >( refElement.getSeries() );
            final ArrayList< RtmTaskSeries > tgtTaskSeriesList = new ArrayList< RtmTaskSeries >( target.get( pos )
                                                                                                       .getSeries() );
            
            Collections.sort( refTaskSeriesList, RtmTaskSeries.LESS_ID );
            Collections.sort( tgtTaskSeriesList, RtmTaskSeries.LESS_ID );
            
            // for each element of the reference list
            for ( Iterator< RtmTaskSeries > j = refTaskSeriesList.iterator(); ok
               && j.hasNext(); )
            {
               final RtmTaskSeries refTaskSeries = j.next();
               
               final int posTaskSeries = Collections.binarySearch( tgtTaskSeriesList,
                                                                   refTaskSeries,
                                                                   RtmTaskSeries.LESS_ID );
               
               IContentProviderSyncOperation taskSeriesOperation = null;
               
               if ( posTaskSeries < 0 )
               {
                  // INSERT: The reference element is not contained in the target
                  // list and is not deleted.
                  if ( !refTaskSeries.isDeleted() )
                  {
                     // SPECIAL CASE MOVE: See Issue#9 http://code.google.com/p/moloko/issues/detail?id=9
                     //
                     // This applies only to incremental sync and happens if a task has moved
                     // from one list to another. Then it appears as new in the new list but not
                     // deleted in the old list.
                     final RtmTaskSeries existingTaskSeries = RtmTaskSeriesProviderPart.getTaskSeries( provider,
                                                                                                       refTaskSeries.getId() );
                     
                     // So check if the taskseries already exists.
                     if ( existingTaskSeries != null )
                     {
                        // UPDATE
                        taskSeriesOperation = ( (IContentProviderSyncable< RtmTaskSeries >) existingTaskSeries ).computeContentProviderUpdateOperation( provider,
                                                                                                                                                        refTaskSeries );
                     }
                     else
                        // INSERT
                        taskSeriesOperation = ( (IContentProviderSyncable< RtmTaskSeries >) refTaskSeries ).computeContentProviderInsertOperation( provider,
                                                                                                                                                   refElement.getId() );
                  }
                  // We never had this taskseries.
                  else
                     taskSeriesOperation = NoopContentProviderSyncOperation.INSTANCE;
               }
               
               // The reference element is contained in the target list.
               else
               {
                  final RtmTaskSeries tgtTaskSeries = tgtTaskSeriesList.get( posTaskSeries );
                  
                  // DELETE: The reference element is explicitly marked as
                  // deleted.
                  if ( refTaskSeries.isDeleted() )
                     taskSeriesOperation = ( (IContentProviderSyncable< RtmTaskSeries >) tgtTaskSeries ).computeContentProviderDeleteOperation( provider );
                  
                  // UPDATE
                  else
                     taskSeriesOperation = ( (IContentProviderSyncable< RtmTaskSeries >) tgtTaskSeries ).computeContentProviderUpdateOperation( provider,
                                                                                                                                                refTaskSeries );
               }
               
               ok = taskSeriesOperation != null;
               
               if ( ok
                  && !( taskSeriesOperation instanceof NoopContentProviderSyncOperation ) )
                  operations.add( taskSeriesOperation );
            }
         }
      }
      
      if ( !ok )
         operations = null;
      
      return operations;
   }
}
