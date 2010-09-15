package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;

import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.rtm.RtmSettings;


public final class RtmSettingsSync
{
   private final static String TAG = RtmSettingsSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< IContentProviderSyncOperation > result )
   {
      RtmSettings server_Settings = null;
      
      try
      {
         server_Settings = service.settings_getList();
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server settings failed.", e );
         
         switch ( e.responseCode )
         {
            case RtmServiceConstants.RtmErrorCodes.LOGIN_FAILED:
            case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
               ++syncResult.stats.numAuthExceptions;
               break;
            case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
               ++syncResult.stats.numIoExceptions;
               break;
            default :
               ++syncResult.stats.numParseExceptions;
               break;
         }
         
         return false;
      }
      
      final RtmSettings local_Settings = RtmSettingsProviderPart.getSettings( provider );
      
      if ( local_Settings == null )
      {
         result.add( server_Settings.computeContentProviderInsertOperation( provider ) );
      }
      else
      {
         result.add( local_Settings.computeContentProviderUpdateOperation( provider,
                                                                           server_Settings ) );
      }
      
      return true;
   }
}
