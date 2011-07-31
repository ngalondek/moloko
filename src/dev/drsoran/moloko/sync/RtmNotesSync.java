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
import android.content.ContentProviderOperation;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;
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
                                      SyncRtmTaskList localTasks,
                                      SyncRtmTaskList serverTasks,
                                      Date lastSync,
                                      MolokoSyncResult syncResult )
   {
      boolean hadOutgoingChanges = false;
      
      // Check if we have server write access
      if ( timeLineFactory != null )
      {
         final List< RtmTaskNote > newNotes = RtmNotesProviderPart.getLocalCreatedNotes( provider );
         
         if ( newNotes != null )
         {
            // Get all local notes and filter them by keeping only the ones (newNotes)
            // from above.
            final SyncRtmTaskNotesList localNotes = localTasks.getSyncNotesList();
            localNotes.intersect( newNotes );
            
            if ( localNotes.size() > 0 )
            {
               sendNewNotes( service,
                             (RtmProvider) provider.getLocalContentProvider(),
                             timeLineFactory,
                             localNotes,
                             syncResult );
               
               hadOutgoingChanges = true;
            }
         }
         else
         {
            syncResult.androidSyncResult.databaseError = true;
            Log.e( TAG, "Getting new created local notes failed." );
         }
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
      
      SyncRtmTaskNotesList server_Notes = serverTasks.getSyncNotesList();
      
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
               
               hadOutgoingChanges = true;
            }
            catch ( ServiceException e )
            {
               handleResponseCode( syncResult, e );
               return false;
            }
         }
      }
      
      final List< IContentProviderSyncOperation > syncOperations = new LinkedList< IContentProviderSyncOperation >();
      
      boolean ok = true;
      // . <-- At this point we have applied all changes to the server, so we have to fetch an up-to-date list
      // of server tasks, modified since we made our note changes. This is needed cause we always have to do
      // a full sync of notes.
      if ( hadOutgoingChanges )
      {
         final SyncRtmTaskList server_Tasks = RtmTasksSync.getServerTasksList( service,
                                                                               lastSync,
                                                                               syncResult );
         ok = server_Tasks != null;
         if ( ok )
         {
            server_Notes = server_Tasks.getSyncNotesList();
         }
         
         // TODO: HACK: RTM does not return a modified Task if deleting a note, so we delete
         // local changes by ourself.
         ok = ok
            && localRemoveDeletedNotes( syncOperations, provider, syncResult );
      }
      
      if ( ok )
      {
         // In case of a full sync, diff all notes. In case of a partial sync we only respect all notes belong
         // to a affected task.
         local_Notes.filterByTaskSeriesIds( server_Notes.getTaskSeriesIds() );
         
         final ContentProviderSyncableList< SyncNote > local_SyncList = new ContentProviderSyncableList< SyncNote >( local_Notes.getSyncNotes(),
                                                                                                                     SyncNote.LESS_ID );
         syncOperations.addAll( SyncDiffer.inDiff( server_Notes.getSyncNotes(),
                                                   local_SyncList,
                                                   true /*
                                                         * We must sync the notes always full cause deleted notes are
                                                         * simply missing and do not appear as deleted.
                                                         */) );
         
         syncResult.localOps.addAll( syncOperations );
      }
      
      return ok;
   }
   
   
   
   private final static boolean localRemoveDeletedNotes( List< IContentProviderSyncOperation > syncOperations,
                                                         ContentProviderClient provider,
                                                         MolokoSyncResult syncResult )
   {
      boolean ok = true;
      
      final List< RtmTaskNote > deletedNotes = RtmNotesProviderPart.getDeletedNotes( provider );
      ok = deletedNotes != null;
      
      if ( ok )
      {
         for ( RtmTaskNote note : deletedNotes )
         {
            syncOperations.add( new SyncNote( null, note ).computeContentProviderDeleteOperation() );
         }
      }
      
      return ok;
   }
   
   
   
   private final static boolean sendNewNotes( Service service,
                                              RtmProvider localContentProvider,
                                              TimeLineFactory timeLineFactory,
                                              SyncRtmTaskNotesList newNotes,
                                              MolokoSyncResult syncResult )
   {
      if ( newNotes.size() > 0 )
      {
         Log.i( TAG, "Sending " + newNotes.size() + " new note(s)" );
         
         RtmTimeline timeline = null;
         
         try
         {
            timeline = timeLineFactory.createTimeline();
         }
         catch ( ServiceException e )
         {
            Log.e( TAG, "Creating new time line failed", e );
            handleResponseCode( syncResult, e );
            return false;
         }
         
         for ( SyncNote note : newNotes.getSyncNotes() )
         {
            sendNote( service, localContentProvider, timeline, note );
         }
      }
      
      return true;
   }
   
   
   
   private final static RtmTaskNote sendNote( Service service,
                                              RtmProvider provider,
                                              RtmTimeline timeline,
                                              SyncNote localNote )
   {
      // Create a new note on RTM side
      final RtmTaskNote serverNote = addNote( timeline, localNote );
      
      if ( serverNote != null )
      {
         final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
         
         localNote.handleAfterServerInsert( new SyncNote( null, serverNote ) )
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
            Log.e( TAG,
                   "Applying local changes after sending new note failed",
                   e );
         }
         finally
         {
            transactionalAccess.endTransaction();
         }
      }
      
      return serverNote;
   }
   
   
   
   private final static RtmTaskNote addNote( RtmTimeline timeline, SyncNote note )
   {
      RtmTaskNote resultNote = null;
      
      try
      {
         final TimeLineResult< RtmTaskNote > res = timeline.tasks_notes_add( note.getListId(),
                                                                             note.getTaskSeriesId(),
                                                                             note.getTaskId(),
                                                                             note.getTitle(),
                                                                             note.getText() )
                                                           .call();
         
         if ( res == null )
            throw new ServiceException( -1,
                                        "ServerOperation produced no result" );
         
         resultNote = res.element;
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Executing server operation failed", e );
      }
      
      return resultNote;
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
                  serverList.update( new SyncNote( null, result ) );
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
