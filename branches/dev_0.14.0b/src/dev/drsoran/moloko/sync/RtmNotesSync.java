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
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.sync.elements.SyncNote;
import dev.drsoran.moloko.sync.elements.SyncRtmTaskList;
import dev.drsoran.moloko.sync.elements.SyncRtmTaskNotesList;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.provider.Rtm.Notes;


public final class RtmNotesSync
{
   private final static String TAG = "Moloko."
      + RtmNotesSync.class.getSimpleName();
   
   

   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      TimeLineFactory timeLineFactory,
                                      SyncRtmTaskList serverTasks,
                                      Date lastSync,
                                      MolokoSyncResult syncResult )
   {
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
         // TODO: Send new notes here
         // final List< RtmTaskSeries > tasks = RtmTaskSeriesProviderPart.getLocalCreatedTaskSeries( provider );
         //
         // if ( tasks != null )
         // {
         // // Send new tasks
         // sendNewTasks( service,
         // (RtmProvider) provider.getLocalContentProvider(),
         // timeLineFactory,
         // tasks,
         // syncResult );
         // }
         // else
         // {
         // syncResult.androidSyncResult.databaseError = true;
         // Log.e( TAG, "Getting new created local tasks failed." );
         // }
      }
      
      SyncRtmTaskNotesList local_Notes;
      {
         final List< RtmTaskNote > notes = RtmNotesProviderPart.getAllNotes( provider );
         
         if ( notes == null )
         {
            syncResult.androidSyncResult.databaseError = true;
            Log.e( TAG, "Getting local notes failed." );
            return false;
         }
         else
            local_Notes = new SyncRtmTaskNotesList( notes );
      }
      
      final SyncRtmTaskNotesList server_Notes = serverTasks.getSyncNotesList();
      
      if ( timeLineFactory != null )
      {
         ModificationSet modifications;
         try
         {
            modifications = SyncAdapter.getModificationsFor( syncResult.context,
                                                             Notes.CONTENT_URI );
         }
         catch ( Throwable e )
         {
            Log.e( TAG, "Retrieving modifications failed", e );
            modifications = new ModificationSet();
         }
         
         boolean doOutSync = modifications.size() > 0;
         
         int numDeleted = 0;
         
         if ( !doOutSync )
         {
            numDeleted = RtmNotesProviderPart.getDeletedNotesCount( provider );
            doOutSync = numDeleted > 0;
         }
         
         if ( doOutSync )
         {
            Log.i( TAG, "Retrieved " + modifications.size()
               + " modification(s) and " + numDeleted + " deletion(s)" );
            
            RtmTimeline timeline = null;
            
            try
            {
               timeline = timeLineFactory.createTimeline();
               
               // Collect all outgoing changes
               final List< IServerSyncOperation< RtmTaskNote > > serverOps = SyncDiffer.outDiff( server_Notes.getSyncNotes(),
                                                                                                 local_Notes.getSyncNotes(),
                                                                                                 SyncNote.LESS_ID,
                                                                                                 modifications,
                                                                                                 timeline,
                                                                                                 lastSync );
               // Send our local changes to the server and update the server list of
               // notes with the new elements retrieved from server during
               // the commit.
               applyServerOperations( (RtmProvider) provider.getLocalContentProvider(),
                                      serverOps,
                                      server_Notes );
            }
            catch ( ServiceException e )
            {
               handleResponseCode( syncResult, e );
               return false;
            }
         }
      }
      
      // . <-- At this point we have an up-to-date list of server notes
      // containing all changes made by outgoing sync.
      {
         final ContentProviderSyncableList< SyncNote > local_SyncList = new ContentProviderSyncableList< SyncNote >( local_Notes.getSyncNotes(),
                                                                                                                     SyncNote.LESS_ID );
         final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.inDiff( server_Notes.getSyncNotes(),
                                                                                         local_SyncList,
                                                                                         lastSync == null );
         syncResult.localOps.addAll( syncOperations );
      }
      
      return true;
   }
   


   private final static void applyServerOperations( RtmProvider rtmProvider /* for deleting modifications */,
                                                    List< ? extends IServerSyncOperation< RtmTaskNote > > serverOps,
                                                    SyncRtmTaskNotesList serverList ) throws ServiceException
   {
      for ( IServerSyncOperation< RtmTaskNote > serverOp : serverOps )
      {
         if ( !( serverOp instanceof INoopSyncOperation ) )
         {
            try
            {
               final RtmTaskNote result = serverOp.execute( rtmProvider );
               
               if ( result != null )
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
