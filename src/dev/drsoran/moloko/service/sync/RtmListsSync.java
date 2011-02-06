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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.lists.ModificationList;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.SyncUtils;


public final class RtmListsSync
{
   private final static String TAG = "Moloko."
      + RtmListsSync.class.getSimpleName();
   
   

   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      RtmTimeline timeline,
                                      ModificationList modifications,
                                      Date lastSyncOut,
                                      SyncResult syncResult,
                                      DirectedSyncOperations result )
   {
      // Get all lists from local database
      final RtmLists local_ListsOfLists = RtmListsProviderPart.getAllLists( provider,
                                                                            null );
      
      if ( local_ListsOfLists == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local lists failed." );
         return false;
      }
      
      RtmLists server_ListOfLists = null;
      
      try
      {
         server_ListOfLists = service.lists_getList();
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
      
      final Collection< RtmList > local_RtmLists = local_ListsOfLists.getLists()
                                                                     .values();
      final Collection< RtmList > server_RtmLists = server_ListOfLists.getLists()
                                                                      .values();
      
      final ContentProviderSyncableList< RtmList > local_SyncList = new ContentProviderSyncableList< RtmList >( local_RtmLists,
                                                                                                                RtmList.LESS_ID );
      final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_RtmLists,
                                                                                    local_SyncList );
      result.getLocalOperations().addAll( syncOperations );
      
      return true;
   }
}
