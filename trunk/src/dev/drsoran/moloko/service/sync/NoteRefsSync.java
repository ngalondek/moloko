package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.moloko.content.NoteRef;
import dev.drsoran.moloko.content.NoteRefsProviderPart;
import dev.drsoran.moloko.content.Queries;
import dev.drsoran.provider.Rtm.NoteRefs;


public final class NoteRefsSync
{
   private final static String TAG = NoteRefsSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         RtmTaskSeries server_TaskSeries,
                                         ArrayList< ContentProviderOperation > result ) throws RemoteException
   {
      boolean ok = server_TaskSeries != null;
      
      if ( ok )
      {
         // Get all lists from local database for the given taskseries
         final ArrayList< NoteRef > local_ListOfNoteRefs = NoteRefsProviderPart.getNoteRefs( provider,
                                                                                             server_TaskSeries.getId() );
         
         ok = local_ListOfNoteRefs != null;
         
         if ( ok )
         {
            final RtmTaskNotes server_notes = server_TaskSeries.getNotes();
            
            boolean deleteAll = server_notes != null;
            
            if ( !deleteAll )
            {
               final List< RtmTaskNote > server_RtmNotes = server_notes.getNotes();
               
               deleteAll = server_RtmNotes == null
                  || server_RtmNotes.size() == 0;
               
               if ( !deleteAll )
               {
                  // do a O(n²) search cause we expect not so much notes per
                  // taskseries.
                  for ( final RtmTaskNote server_note : server_RtmNotes )
                  {
                     int idx = -1;
                     
                     for ( int i = 0; i < local_ListOfNoteRefs.size(); ++i )
                     {
                        if ( local_ListOfNoteRefs.get( i ).getNoteId() == server_note.getId() )
                        {
                           idx = i;
                        }
                     }
                                          
                     // INSERT:
                     if ( idx == -1 )
                     {
                        
                     }
                     
                     // UPDATE:
                     else
                     {
                        
                     }
                  }
               }
            }
            
            if ( ok && deleteAll )
            {
               Log.d( TAG, "Deleting all note references." );
               in_deleteAllNoteRefs( provider, local_ListOfNoteRefs, result );
            }
         }
      }
      
      return ok;
   }
   


   private static void in_deleteAllNoteRefs( ContentProviderClient provider,
                                             ArrayList< NoteRef > local_ListOfNoteRefs,
                                             ArrayList< ContentProviderOperation > result )
   {
      for ( final NoteRef local_NoteRef : local_ListOfNoteRefs )
      {
         result.add( ContentProviderOperation.newDelete( Queries.contentUriWithId( NoteRefs.CONTENT_URI,
                                                                                   local_NoteRef.getId() ) )
                                             .build() );
      }
   }
}
