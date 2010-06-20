package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;


public final class RtmListsSync
{
   private final static String TAG = RtmListsSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< IContentProviderSyncOperation > result )
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
         
         switch ( e.responseCode )
         {
            case RtmServiceConstants.RtmErrorCodes.LOGIN_FAILED:
            case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
               ++syncResult.stats.numAuthExceptions;
            case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
               ++syncResult.stats.numIoExceptions;
            default :
               ++syncResult.stats.numParseExceptions;
         }
         
         return false;
      }
      
      final Collection< RtmList > local_RtmLists = local_ListsOfLists.getLists()
                                                                     .values();
      final Collection< RtmList > server_RtmLists = server_ListOfLists.getLists()
                                                                      .values();
      
      final ContentProviderSyncableList< RtmList > local_SyncList = new ContentProviderSyncableList< RtmList >( provider,
                                                                                                                local_RtmLists,
                                                                                                                RtmList.LESS_ID );
      
      final ArrayList< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_RtmLists,
                                                                                         local_SyncList );
      
      boolean ok = syncOperations != null;
      
      if ( ok )
      {
         result.addAll( syncOperations );
      }
      
      return ok;
   }
}
