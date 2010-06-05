package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.service.sync.SyncAdapter.Direction;
import dev.drsoran.provider.Rtm.Lists;


public final class RtmTasksSync
{
   private final static String TAG = RtmTasksSync.class.getSimpleName();
   
   

   public static boolean computeSync( ContentProviderClient provider,
                                      ServiceImpl service,
                                      SyncResult syncResult,
                                      ArrayList< ContentProviderOperation > result )
   {
      RtmTasks local_Tasks = null;
      RtmTasks server_Tasks = null;
      
      // Get all lists from local database
      try
      {
         local_Tasks = RtmTaskSeriesProviderPart.getAllTaskSeries( provider );
      }
      catch ( RemoteException e )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local lists failed.", e );
         return false;
      }
      
      try
      {
         server_Tasks = service.tasks_getList( null, null, null );
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server lists failed.", e );
         return false;
      }
      
      boolean ok = true;
      
      // This assumes that the local lists are up-to-date. This means the
      // two sets below should contain identical RtmTaskList
      final List< RtmTaskList > local_ListsOfTaskLists = local_Tasks.getLists();
      final List< RtmTaskList > server_ListsOfTaskLists = server_Tasks.getLists();
      
      // We sort the local lists by their list ID. So we can perform a binary search
      // with the server list IDs.
      Collections.sort( local_ListsOfTaskLists );
      
      // for each server list
      for ( Iterator< RtmTaskList > iterator = server_ListsOfTaskLists.iterator(); ok
         && iterator.hasNext(); )
      {
         final RtmTaskList server_RtmTaskList = iterator.next();
         
         final int idx = Collections.binarySearch( local_ListsOfTaskLists,
                                                   server_RtmTaskList );
         
         // INSERT: if we do not have the list, add it
         if ( localRtmList == null )
         {
            ok = result.add( RtmListsProviderPart.insertOrReplace( serverRtmList ) );
            ++syncResult.stats.numInserts;
         }
         
         // UPDATE: if we have the list but check if content changed
         else
         {
            final int numUpdates = localRtmList.computeSyncWith( Direction.IN,
                                                                 serverRtmList,
                                                                 result );
            ok = numUpdates != -1;
            syncResult.stats.numUpdates += numUpdates;
         }
      }
      
      return ok;
   }
   


   private static boolean syncRtmTaskList( RtmTaskList lhs,
                                           RtmTaskList rhs,
                                           int direction,
                                           ArrayList< ContentProviderOperation > operations,
                                           SyncResult result )
   {
      boolean ok = true;
      
      switch ( direction )
      {
         case SyncAdapter.Direction.IN:
            break;
         case SyncAdapter.Direction.OUT:
            Log.e( TAG, "Unsupported sync direction: OUT" );
         default :
            ok = false;
            break;
      }
      
      return ok;
   }
}
