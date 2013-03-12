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

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.RtmServiceConstants;
import dev.drsoran.moloko.content.db.RtmContactsTable;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.rtm.RtmContact;
import dev.drsoran.rtm.RtmContacts;


public final class RtmContactsSync
{
   private final static Class< RtmContactsSync > TAG = RtmContactsSync.class;
   
   
   
   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      Date lastSync,
                                      MolokoSyncResult syncResult )
   {
      // Get all contacts from local database
      final List< RtmContact > local_ListOfContacts = RtmContactsTable.getAllContacts( provider,
                                                                                              null );
      
      if ( local_ListOfContacts == null )
      {
         syncResult.androidSyncResult.databaseError = true;
         MolokoApp.Log.e( TAG, "Getting local contacts failed." );
         return false;
      }
      
      RtmContacts server_ListOfContacts = null;
      
      try
      {
         server_ListOfContacts = service.contacts_getList();
      }
      catch ( ServiceException e )
      {
         MolokoApp.Log.e( TAG, "Getting server contacts failed.", e );
         
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
      
      final List< RtmContact > server_Contacts = server_ListOfContacts.getContacts();
      
      final ContentProviderSyncableList< RtmContact > local_SyncList = new ContentProviderSyncableList< RtmContact >( local_ListOfContacts,
                                                                                                                      RtmContact.LESS_ID );
      final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_Contacts,
                                                                                      local_SyncList,
                                                                                      true /* always full sync */);
      
      syncResult.localOps.addAll( syncOperations );
      
      return true;
   }
}
