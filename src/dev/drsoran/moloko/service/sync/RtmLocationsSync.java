package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.content.RtmLocationsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;


public final class RtmLocationsSync
{
   private final static String TAG = RtmLocationsSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< IContentProviderSyncOperation > operations )
   {
      // Get all locations from local database
      final ArrayList< RtmLocation > local_Locations = RtmLocationsProviderPart.getAllLocations( provider );
      
      if ( local_Locations == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local locations failed." );
         return false;
      }
      
      List< RtmLocation > server_Locations = null;
      
      try
      {
         server_Locations = service.locations_getList();
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
      
      boolean ok = true;
      
      // Sync location lists
      final ContentProviderSyncableList< RtmLocation > local_SyncList = new ContentProviderSyncableList< RtmLocation >( provider,
                                                                                                                        local_Locations,
                                                                                                                        RtmLocation.LESS_ID );
      
      final ArrayList< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_Locations,
                                                                                         local_SyncList );
      
      ok = syncOperations != null;
      
      if ( ok )
      {
         operations.addAll( syncOperations );
      }
      
      return ok;
   }
}
