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

import android.content.ContentProviderClient;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.RtmServiceConstants;
import dev.drsoran.moloko.content.db.RtmSettingsTable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.rtm.RtmSettings;


public final class RtmSettingsSync
{
   private final static Class< RtmSettingsSync > TAG = RtmSettingsSync.class;
   
   
   
   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      MolokoSyncResult syncResult )
   {
      RtmSettings server_Settings = null;
      
      try
      {
         server_Settings = service.settings_getList();
      }
      catch ( ServiceException e )
      {
         MolokoApp.Log.e( TAG, "Getting server settings failed.", e );
         
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
      
      final RtmSettings local_Settings = RtmSettingsTable.getSettings( provider );
      
      if ( local_Settings == null )
      {
         syncResult.localOps.add( server_Settings.computeContentProviderInsertOperation() );
      }
      else
      {
         syncResult.localOps.add( local_Settings.computeContentProviderUpdateOperation( server_Settings ) );
      }
      
      return true;
   }
}
