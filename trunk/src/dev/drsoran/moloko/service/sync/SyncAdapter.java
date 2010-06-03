package dev.drsoran.moloko.service.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.ParseException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.provider.Rtm.Lists;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
   private static final String TAG = SyncAdapter.class.getSimpleName();
   
   private final AccountManager accountManager;
   
   private final Context context;
   
   private Date lastUpdated;
   
   private ServiceImpl serviceImpl;
   
   

   public SyncAdapter( Context context, boolean autoInitialize )
   {
      super( context, autoInitialize );
      
      this.context = context;
      this.accountManager = AccountManager.get( context );
   }
   


   @Override
   public void onPerformSync( Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult )
   {
      Debug.waitForDebugger();
      
      String authToken = null;
      
      try
      {
         // use the account manager to request the credentials
         authToken = accountManager.blockingGetAuthToken( account,
                                                          Constants.AUTH_TOKEN_TYPE,
                                                          true /* notifyAuthFailure */);
         
         if ( authToken != null )
         {
            // Check if we have all account info
            final String apiKey = accountManager.getUserData( account,
                                                              Constants.FEAT_API_KEY );
            final String sharedSecret = accountManager.getUserData( account,
                                                                    Constants.FEAT_SHARED_SECRET );
            
            if ( apiKey == null || sharedSecret == null )
            {
               accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                   authToken );
            }
            else
            {
               serviceImpl = new ServiceImpl( new ApplicationInfo( apiKey,
                                                                   sharedSecret,
                                                                   context.getString( R.string.app_name ),
                                                                   authToken ) );
               
               final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
               
               if ( computeDataSets( provider, syncResult, operations ) )
               {
                  try
                  {
                     provider.applyBatch( operations );
                     
                     lastUpdated = new Date();
                  }
                  catch ( RemoteException e )
                  {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  catch ( OperationApplicationException e )
                  {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
               }
               else
               {
                  clearSyncResult( syncResult );
               }
            }
         }
      }
      catch ( final AuthenticatorException e )
      {
         syncResult.stats.numParseExceptions++;
         Log.e( TAG, "AuthenticatorException", e );
      }
      catch ( final OperationCanceledException e )
      {
         Log.e( TAG, "OperationCanceledExcetpion", e );
      }
      catch ( final IOException e )
      {
         Log.e( TAG, "IOException", e );
         syncResult.stats.numIoExceptions++;
      }
      catch ( final ParseException e )
      {
         syncResult.stats.numParseExceptions++;
         Log.e( TAG, "ParseException", e );
      }
      catch ( ServiceInternalException e )
      {
         syncResult.stats.numIoExceptions++;
         Log.e( TAG, "ServiceInternalException", e );
      }
   }
   


   private boolean computeDataSets( ContentProviderClient provider,
                                    SyncResult syncResult,
                                    ArrayList< ContentProviderOperation > result )
   {
      boolean ok = computeLists( provider, syncResult, result );
      
      ok = ok && syncResult.madeSomeProgress();
      
      return ok;
   }
   


   private boolean computeLists( ContentProviderClient provider,
                                 SyncResult syncResult,
                                 ArrayList< ContentProviderOperation > result )
   {
      RtmLists localListsofLists = null;
      RtmLists serverListofLists = null;
      
      // Get all lists from local database
      try
      {
         localListsofLists = RtmListsProviderPart.getAllLists( provider );
      }
      catch ( RemoteException e )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local lists failed.", e );
         return false;
      }
      
      try
      {
         serverListofLists = serviceImpl.lists_getList();
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server lists failed.", e );
         return false;
      }
      
      boolean ok = true;
      
      final Map< String, RtmList > localLists = localListsofLists.getLists();
      final Map< String, RtmList > serverLists = serverListofLists.getLists();
      final Collection< RtmList > localRtmLists = localListsofLists.getLists()
                                                                   .values();
      final Collection< RtmList > serverRtmLists = serverListofLists.getLists()
                                                                    .values();
      
      // for each list on the server
      for ( Iterator< RtmList > iterator = serverRtmLists.iterator(); ok
         && iterator.hasNext(); )
      {
         final RtmList serverRtmList = iterator.next();
         final RtmList localRtmList = localLists.get( serverRtmList.getId() );
         
         // INSERT: if we do not have the list, add it
         if ( localRtmList == null )
         {
            ok = result.add( RtmListsProviderPart.insertOrReplace( serverRtmList ) );
            ++syncResult.stats.numInserts;
         }
         
         // UPDATE: if we have the list but content changed
         else
         {
            final ContentValues values = new ContentValues();
            
            if ( !localRtmList.getId().equals( serverRtmList.getId() ) )
            {
               values.put( Lists._ID, serverRtmList.getId() );
            }
            if ( !localRtmList.getName().equals( serverRtmList.getName() ) )
            {
               values.put( Lists.NAME, serverRtmList.getName() );
            }
            
            if ( values.size() > 0 )
            {
               ok = result.add( RtmListsProviderPart.updateList( localRtmList.getId(),
                                                                 values ) );
               ++syncResult.stats.numUpdates;
            }
         }
      }
      
      // DELETE: if we have a list that is no longer on the server. This has moved all
      // tasks of this list to Inbox.
      // TODO: What to do with the tasks? Add option to move to Inbox or delete tasks.
      for ( Iterator< RtmList > iterator = localRtmLists.iterator(); ok
         && iterator.hasNext(); )
      {
         final RtmList localRtmList = iterator.next();
         final RtmList serverRtmList = serverLists.get( localRtmList.getId() );
         
         if ( serverRtmList == null )
         {
            ok = result.add( RtmListsProviderPart.deleteList( localRtmList.getId() ) );
            ++syncResult.stats.numUpdates;
         }
      }
      
      return ok;
   }
   


   @SuppressWarnings( "unused" )
   private boolean checkAuthToken( String authToken ) throws IOException,
                                                     ParseException
   {
      boolean valid = false;
      
      try
      {
         serviceImpl.auth_checkToken( authToken );
         valid = true;
      }
      catch ( ServiceException e )
      {
         switch ( e.responseCode )
         {
            case RtmServiceConstants.RtmErrorCodes.INVALID_AUTH_TOKEN:
            case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
               valid = false;
               break;
            case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
               throw new IOException( e.responseMessage );
            default :
               throw new ParseException( e.responseMessage );
         }
      }
      
      return valid;
   }
   


   private static  void clearSyncResult( SyncResult syncResult )
   {
      syncResult.stats.numInserts = 0;
      syncResult.stats.numUpdates = 0;
      syncResult.stats.numDeletes = 0;
   }
}
