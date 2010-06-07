package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.service.sync.SyncAdapter.Direction;


public final class RtmTasksSync
{
   private final static String TAG = RtmTasksSync.class.getSimpleName();
   
   

   public static boolean computeSync( ContentProviderClient provider,
                                      ServiceImpl service,
                                      SyncResult syncResult,
                                      ArrayList< ContentProviderOperation > result ) throws RemoteException
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
      
      final List< RtmTaskList > server_ListsOfTaskLists = server_Tasks.getLists();
      
      // Here we have to clone the list cause the returned list can not be modified nor
      // sorted.
      final ArrayList< RtmTaskList > local_ListsOfTaskLists = new ArrayList< RtmTaskList >();
      local_ListsOfTaskLists.addAll( local_Tasks.getLists() );
      
      Collections.sort( local_ListsOfTaskLists );
      
      final int length = server_ListsOfTaskLists.size();
      
      for ( int i = 0; ok && i < length; i++ )
      {
         final RtmTaskList server_RtmTaskList = server_ListsOfTaskLists.get( i );
         
         RtmTaskList local_RtmTaskList = null;
         
         final int pos = Collections.binarySearch( local_ListsOfTaskLists,
                                                   server_RtmTaskList );
         
         // if not found, create a new, empty list
         if ( pos < 0 )
         {
            local_RtmTaskList = new RtmTaskList( server_RtmTaskList.getId() );
         }
         else
         {
            local_RtmTaskList = local_ListsOfTaskLists.get( pos );
         }
         
         // Sync lists
         ok = syncRtmTaskList( provider,
                               local_RtmTaskList,
                               server_RtmTaskList,
                               Direction.IN,
                               result,
                               syncResult );
      }
      
      return ok;
   }
   


   private static boolean syncRtmTaskList( ContentProviderClient provider,
                                           RtmTaskList lhs,
                                           RtmTaskList rhs,
                                           int direction,
                                           ArrayList< ContentProviderOperation > operations,
                                           SyncResult result ) throws RemoteException
   {
      boolean ok = true;
      
      switch ( direction )
      {
         case SyncAdapter.Direction.IN:

            final List< RtmTaskSeries > rhs_TaskSeriesList = rhs.getSeries();
            
            // Here we have to clone the list cause the returned list can not be modified nor
            // sorted.
            final ArrayList< RtmTaskSeries > lhs_TaskSeriesList = new ArrayList< RtmTaskSeries >();
            lhs_TaskSeriesList.addAll( lhs.getSeries() );
            
            // Sort the reference list by their taskseries IDs.
            Collections.sort( lhs_TaskSeriesList );
            
            // For each element from the reference list
            for ( Iterator< RtmTaskSeries > i = rhs_TaskSeriesList.iterator(); ok
               && i.hasNext(); )
            {
               final RtmTaskSeries rhs_RtmTaskSeries = i.next();
               
               final int idx = Collections.binarySearch( lhs_TaskSeriesList,
                                                         rhs_RtmTaskSeries );
               
               // INSERT: if we do not have the taskseries in the lhs list
               if ( idx < 0 )
               {
                  final List< ContentProviderOperation > insertOperations = RtmTaskSeriesProviderPart.insertTaskSeries( provider,
                                                                                                                        lhs.getId(),
                                                                                                                        rhs_RtmTaskSeries );
                  ok = insertOperations != null;
                  if ( ok )
                     operations.addAll( insertOperations );
                  
                  ++result.stats.numInserts;
               }
               
               // UPDATE: if we have the taskseries in the lhs list check if content changed
               else
               {
                  // TODO: Implement update check of taskseries
               }
            }
            
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
