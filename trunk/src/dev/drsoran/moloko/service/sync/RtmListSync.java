package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.content.Queries;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.service.sync.SyncAdapter.Direction;
import dev.drsoran.provider.Rtm.Lists;


public final class RtmListSync
{
   private final static String TAG = RtmListSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< ContentProviderOperation > result )
   {
      // Get all lists from local database
      final RtmLists local_ListsOfLists = RtmListsProviderPart.getAllLists( provider );
      
      if ( local_ListsOfLists == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local lists failed." );
         return false;
      }
      
      RtmLists server_ListOfLists = null;
      
      try
      {
         server_ListOfLists = service.lists_getList();
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server lists failed.", e );
         return false;
      }
      
      boolean ok = true;
      
      final Map< String, RtmList > local_Lists = local_ListsOfLists.getLists();
      final Map< String, RtmList > server_Lists = server_ListOfLists.getLists();
      final Collection< RtmList > local_RtmLists = local_ListsOfLists.getLists()
                                                                     .values();
      final Collection< RtmList > server_RtmLists = server_ListOfLists.getLists()
                                                                      .values();
      
      // for each list on the server
      for ( Iterator< RtmList > iterator = server_RtmLists.iterator(); ok
         && iterator.hasNext(); )
      {
         final RtmList server_RtmList = iterator.next();
         final RtmList local_RtmList = local_Lists.get( server_RtmList.getId() );
         
         // INSERT: if we do not have the list, add it
         if ( local_RtmList == null )
         {
            final ContentProviderOperation insertOperation = RtmListsProviderPart.insert( provider,
                                                                                          server_RtmList );
            
            ok = insertOperation != null;
            if ( ok )
            {
               result.add( insertOperation );
               ++syncResult.stats.numInserts;
            }
         }
         
         // UPDATE: if we have the list but check if content changed
         else
         {
            ok = in_syncRtmLists( local_RtmList,
                                  server_RtmList,
                                  Direction.IN,
                                  result,
                                  syncResult );
         }
      }
      
      // DELETE: if we have a list that is no longer on the server. This has moved all
      // tasks of this list to Inbox.
      // TODO: What to do with the tasks? Add option to move to Inbox or delete tasks.
      //
      // for each local list
      for ( Iterator< RtmList > iterator = local_RtmLists.iterator(); ok
         && iterator.hasNext(); )
      {
         final RtmList local_RtmList = iterator.next();
         final RtmList server_RtmList = server_Lists.get( local_RtmList.getId() );
         
         if ( server_RtmList == null )
         {
            result.add( RtmListsProviderPart.deleteList( local_RtmList.getId() ) );
            ++syncResult.stats.numDeletes;
         }
      }
      
      return ok;
   }
   


   private static boolean in_syncRtmLists( RtmList lhs,
                                           RtmList rhs,
                                           int direction,
                                           ArrayList< ContentProviderOperation > operations,
                                           SyncResult result )
   {
      boolean ok = lhs.getId().equals( rhs.getId() );
      
      if ( ok )
      {
         final ContentValues contentValues = RtmListsProviderPart.getContentValues( rhs,
                                                                                    false );
         
         operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                       lhs.getId() ) )
                                                 .withValues( contentValues )
                                                 .build() );
         ++result.stats.numUpdates;
      }
      
      return ok;
   }
}
