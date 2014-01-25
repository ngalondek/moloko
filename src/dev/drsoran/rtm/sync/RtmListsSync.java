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

package dev.drsoran.rtm.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.RtmServiceConstants;
import dev.drsoran.moloko.app.sync.SyncAdapter;
import dev.drsoran.moloko.content.ContentRepository;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.content.db.RtmTasksListsTable;
import dev.drsoran.moloko.content.db.TableColumns.Lists;
import dev.drsoran.moloko.sync.elements.SyncRtmList;
import dev.drsoran.moloko.sync.elements.SyncRtmListsList;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;


public final class RtmListsSync
{
   private final static Class< RtmListsSync > TAG = RtmListsSync.class;
   
   
   
   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      TimeLineFactory timeLineFactory,
                                      Date lastSync,
                                      MolokoSyncResult syncResult )
   {
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
         final List< RtmList > newLists = RtmTasksListsTable.getLocalCreatedLists( provider );
         
         if ( newLists != null )
         {
            sendNewLists( service,
                          (ContentRepository) provider.getLocalContentProvider(),
                          timeLineFactory,
                          new SyncRtmListsList( newLists ),
                          syncResult );
         }
         else
         {
            syncResult.androidSyncResult.databaseError = true;
            MolokoApp.Log.e( TAG, "Getting new created local lists failed." );
         }
      }
      
      SyncRtmListsList local_Lists = null;
      {
         // Get all lists from local database
         final RtmLists local_ListsOfLists = RtmTasksListsTable.getAllLists( provider,
                                                                               null );
         
         if ( local_ListsOfLists == null )
         {
            syncResult.androidSyncResult.databaseError = true;
            MolokoApp.Log.e( TAG, "Getting local lists failed." );
            return false;
         }
         
         local_Lists = new SyncRtmListsList( local_ListsOfLists.getListsPlain() );
      }
      
      SyncRtmListsList server_Lists = null;
      {
         try
         {
            final RtmLists server_ListOfLists = service.lists_getList();
            server_Lists = new SyncRtmListsList( server_ListOfLists.getListsPlain() );
         }
         catch ( ServiceException e )
         {
            MolokoApp.Log.e( TAG, "Getting server lists failed.", e );
            handleResponseCode( syncResult, e );
            return false;
         }
      }
      
      if ( timeLineFactory != null )
      {
         ModificationSet modifications;
         try
         {
            modifications = SyncAdapter.getModificationsFor( syncResult.context,
                                                             Lists.CONTENT_URI );
         }
         catch ( Throwable e )
         {
            MolokoApp.Log.e( TAG, "Retrieving modifications failed", e );
            modifications = new ModificationSet();
         }
         
         boolean doOutSync = modifications.size() > 0;
         
         int numDeleted = 0;
         
         if ( !doOutSync )
         {
            numDeleted = RtmTasksListsTable.getDeletedListsCount( provider );
            doOutSync = numDeleted > 0;
         }
         
         if ( doOutSync )
         {
            MolokoApp.Log.d( TAG, "Retrieved " + modifications.size()
               + " modification(s) and " + numDeleted + " deletion(s)" );
            
            RtmTimeline timeline = null;
            
            try
            {
               timeline = timeLineFactory.createTimeline();
               
               // Collect all outgoing changes
               final List< IServerSyncOperation< RtmList > > serverOps = SyncDiffer.outDiff( server_Lists.getSyncLists(),
                                                                                             local_Lists.getSyncLists(),
                                                                                             SyncRtmList.LESS_ID,
                                                                                             modifications,
                                                                                             timeline,
                                                                                             lastSync );
               // Send our local changes to the server and update the server list of
               // lists with the new elements retrieved from server during
               // the commit.
               applyServerOperations( (ContentRepository) provider.getLocalContentProvider(),
                                      serverOps,
                                      server_Lists );
            }
            catch ( ServiceException e )
            {
               handleResponseCode( syncResult, e );
               return false;
            }
         }
      }
      
      // . <-- At this point we have an up-to-date list of server lists
      // containing all changes made by outgoing sync.
      {
         final ContentProviderSyncableList< SyncRtmList > local_SyncList = new ContentProviderSyncableList< SyncRtmList >( local_Lists.getSyncLists(),
                                                                                                                           SyncRtmList.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_Lists.getSyncLists(),
                                                                                         local_SyncList,
                                                                                         true /* always full sync */);
         syncResult.localOps.addAll( syncOperations );
      }
      
      return true;
   }
   
   
   
   private final static boolean sendNewLists( Service service,
                                              ContentRepository localContentProvider,
                                              TimeLineFactory timeLineFactory,
                                              SyncRtmListsList newLists,
                                              MolokoSyncResult syncResult )
   {
      if ( newLists.size() > 0 )
      {
         MolokoApp.Log.d( TAG, "Sending " + newLists.size() + " new list(s)" );
         
         RtmTimeline timeline = null;
         
         try
         {
            timeline = timeLineFactory.createTimeline();
         }
         catch ( ServiceException e )
         {
            MolokoApp.Log.e( TAG, "Creating new time line failed", e );
            handleResponseCode( syncResult, e );
            return false;
         }
         
         for ( SyncRtmList list : newLists.getSyncLists() )
         {
            sendList( service, localContentProvider, timeline, list );
         }
      }
      
      return true;
   }
   
   
   
   private final static RtmList sendList( Service service,
                                          ContentRepository provider,
                                          RtmTimeline timeline,
                                          SyncRtmList localList )
   {
      // Create a new list on RTM side
      final RtmList serverList = addList( timeline, localList );
      
      if ( serverList != null )
      {
         final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
         
         localList.handleAfterServerInsert( new SyncRtmList( serverList ) )
                  .getBatch( operations );
         
         final TransactionalAccess transactionalAccess = provider.newTransactionalAccess();
         try
         {
            transactionalAccess.beginTransaction();
            
            provider.applyBatch( operations );
            
            transactionalAccess.setTransactionSuccessful();
         }
         catch ( Throwable e )
         {
            MolokoApp.Log.e( TAG,
                             "Applying local changes after sending new list failed",
                             e );
         }
         finally
         {
            transactionalAccess.endTransaction();
         }
      }
      
      return serverList;
   }
   
   
   
   private final static RtmList addList( RtmTimeline timeline, SyncRtmList list )
   {
      RtmList resultList = null;
      
      try
      {
         final TimeLineResult< RtmList > res = timeline.lists_add( list.getName(),
                                                                   list.getSmartFilter() != null
                                                                                                ? list.getSmartFilter()
                                                                                                      .getFilterString()
                                                                                                : null )
                                                       .call();
         
         if ( res == null )
            throw new ServiceException( -1,
                                        "ServerOperation produced no result" );
         
         resultList = res.element;
      }
      catch ( ServiceException e )
      {
         MolokoApp.Log.e( TAG, "Executing server operation failed", e );
      }
      
      return resultList;
   }
   
   
   
   private final static void applyServerOperations( ContentRepository rtmProvider /* for deleting modifications */,
                                                    List< ? extends IServerSyncOperation< RtmList > > serverOps,
                                                    SyncRtmListsList serverList ) throws ServiceException
   {
      for ( IServerSyncOperation< RtmList > serverOp : serverOps )
      {
         if ( !( serverOp instanceof INoopSyncOperation ) )
         {
            try
            {
               final RtmList result = serverOp.execute( rtmProvider );
               
               if ( result != null )
                  // If the set already contains an element in respect to the Comparator,
                  // then we update it by the new received.
                  serverList.update( new SyncRtmList( result ) );
            }
            catch ( ServiceException e )
            {
               MolokoApp.Log.e( TAG, "Applying server operation failed", e );
               throw e;
            }
         }
      }
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
