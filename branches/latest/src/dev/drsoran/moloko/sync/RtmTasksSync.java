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

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.rtm.SyncTask;


public final class RtmTasksSync
{
   private final static String TAG = "Moloko."
      + RtmTasksSync.class.getSimpleName();
   
   

   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      RtmTimeline timeline,
                                      ModificationList modifications,
                                      Date lastSyncOut,
                                      MolokoSyncResult syncResult )
   {
      final List< SyncTask > local_Tasks = toPlainList( RtmTaskSeriesProviderPart.getAllTaskSeries( provider ) );
      
      if ( local_Tasks == null )
      {
         syncResult.androidSyncResult.databaseError = true;
         Log.e( TAG, "Getting local tasks failed." );
         return false;
      }
      
      List< SyncTask > server_Tasks;
      
      try
      {
         server_Tasks = toPlainList( service.tasks_getList( null,
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
      
      // Check if we have server write access
      if ( timeline != null )
      {
         // Do a 2-way diff
         final DirectedSyncOperations< RtmTaskSeries > syncOperations = SyncDiffer.twoWaydiff( server_Tasks,
                                                                                               local_Tasks,
                                                                                               SyncTask.LESS_ID,
                                                                                               modifications,
                                                                                               timeline,
                                                                                               lastSyncOut );
         syncResult.localOps.addAll( syncOperations.getLocalOperations() );
         syncResult.serverOps.addAll( syncOperations.getServerOperations() );
      }
      else
      {
         // Only sync incoming
         final ContentProviderSyncableList< SyncTask > local_SyncList = new ContentProviderSyncableList< SyncTask >( local_Tasks,
                                                                                                                     SyncTask.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_Tasks,
                                                                                       local_SyncList );
         syncResult.localOps.addAll( syncOperations );
      }
      
      return true;
   }
   


   private final static List< SyncTask > toPlainList( RtmTasks tasks )
   {
      if ( tasks == null )
         return null;
      
      final List< SyncTask > result = new LinkedList< SyncTask >();
      
      for ( RtmTaskList rtmTaskList : tasks.getLists() )
         for ( RtmTaskSeries series : rtmTaskList.getSeries() )
            for ( RtmTask task : series.getTasks() )
               result.add( new SyncTask( series, task ) );
      
      return new ArrayList< SyncTask >( result );
   }
   
}
