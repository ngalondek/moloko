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

import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.RtmContactsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.SyncUtils;
import dev.drsoran.rtm.RtmContact;
import dev.drsoran.rtm.RtmContacts;


public final class RtmContactsSync
{
   private final static String TAG = "Moloko."
      + RtmContactsSync.class.getSimpleName();
   
   

   public static boolean computeSync( ContentProviderClient provider,
                                      RtmTimeline timeline,
                                      Date lastSyncOut,
                                      SyncResult syncResult,
                                      DirectedSyncOperations result )
   {
      // Get all contacts from local database
      final List< RtmContact > local_ListOfContacts = RtmContactsProviderPart.getAllContacts( provider,
                                                                                              null );
      
      if ( local_ListOfContacts == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local contacts failed." );
         return false;
      }
      
      RtmContacts server_ListOfContacts = null;
      
      try
      {
         server_ListOfContacts = timeline.getService().contacts_getList();
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server contacts failed.", e );
         
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
      
      final List< RtmContact > server_Contacts = server_ListOfContacts.getContacts();
      
      final ContentProviderSyncableList< RtmContact > local_SyncList = new ContentProviderSyncableList< RtmContact >( local_ListOfContacts,
                                                                                                                      RtmContact.LESS_ID );
      final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_Contacts,
                                                                                    local_SyncList );
      
      result.getLocalOperations().addAll( syncOperations );
      
      return true;
   }
}
