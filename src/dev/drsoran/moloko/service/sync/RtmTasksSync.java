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
import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ModificationList;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.SyncUtils;


public final class RtmTasksSync
{
   private final static String TAG = "Moloko."
      + RtmTasksSync.class.getSimpleName();
   
   

   public static boolean computeSync( ContentProviderClient provider,
                                      RtmTimeline timeline,
                                      ModificationList modifications,
                                      Date lastSyncOut,
                                      SyncResult syncResult,
                                      DirectedSyncOperations operations )
   {
      final List< RtmTaskSeries > local_Tasks = toPlainList( RtmTaskSeriesProviderPart.getAllTaskSeries( provider ) );
      
      if ( local_Tasks == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local tasks failed." );
         return false;
      }
      
      List< RtmTaskSeries > server_Tasks;
      
      try
      {
         server_Tasks = toPlainList( timeline.getService()
                                             .tasks_getList( null,
                                                             null,
                                                             lastSyncOut ) );
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
                  SyncUtils.handleServiceInternalException( (ServiceInternalException) e,
                                                            TAG,
                                                            syncResult );
               else
                  ++syncResult.stats.numParseExceptions;
               break;
         }
         
         return false;
      }
      
      final DirectedSyncOperations syncOperations = SyncDiffer.twoWaydiff( server_Tasks,
                                                                           local_Tasks,
                                                                           RtmTaskSeries.LESS_ID,
                                                                           modifications,
                                                                           timeline,
                                                                           lastSyncOut,
                                                                           lastSyncOut == null );
      operations.addAll( syncOperations );
      
      return true;
   }
   


   private final static List< RtmTaskSeries > toPlainList( RtmTasks tasks )
   {
      if ( tasks == null )
         return null;
      
      final List< RtmTaskSeries > result = new ArrayList< RtmTaskSeries >();
      final List< RtmTaskList > listOfLists = tasks.getLists();
      
      for ( RtmTaskList rtmTaskList : listOfLists )
      {
         result.addAll( rtmTaskList.getSeries() );
      }
      
      return result;
   }
}
