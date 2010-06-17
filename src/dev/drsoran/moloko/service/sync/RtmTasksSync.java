package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;


public final class RtmTasksSync
{
   private final static String TAG = RtmTasksSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< ContentProviderSyncOperation > operations ) throws RemoteException
   {
      // Get all lists from local database
      final RtmTasks local_Tasks = RtmTaskSeriesProviderPart.getAllTaskSeries( provider );
      
      if ( local_Tasks == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local tasks failed." );
         return false;
      }
      
      RtmTasks server_Tasks = null;
      
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
      
      // Sync task lists
      final ContentProviderSyncableList< RtmTaskList > local_SyncList = new ContentProviderSyncableList< RtmTaskList >( provider,
                                                                                                                        local_Tasks.getLists(),
                                                                                                                        RtmTaskList.LESS_ID );
      
      final ArrayList< ContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_Tasks.getLists(),
                                                                                        local_SyncList );
      
      ok = syncOperations != null;
      
      if ( ok )
      {
         operations.addAll( syncOperations );
      }
      
      return ok;
   }
   
}
