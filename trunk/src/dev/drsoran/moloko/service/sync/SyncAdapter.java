package dev.drsoran.moloko.service.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.ParseException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
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

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
   public final static class Operation
   {
      public final static int INSERT = 0x1;
      
      public final static int UPDATE = 0x2;
      
      public final static int DELETE = 0x4;
   }
   

   public final static class Direction
   {
      public final static int IN = 0;
      
      public final static int OUT = 1;
   }
   
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
      // TODO: Remove debug wait
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
               
               if ( computeOperationsBatch( provider, syncResult ) )
               {
                  lastUpdated = new Date();
                  Log.i( TAG, "Sync succeded." + syncResult.toDebugString() );
               }
               else
               {
                  Log.e( TAG, "Sync failed." + syncResult.toDebugString() );
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
   


   private boolean computeOperationsBatch( ContentProviderClient provider,
                                           SyncResult syncResult )
   {
      boolean ok = true;
      
      final ArrayList< ContentProviderSyncOperation > operations = new ArrayList< ContentProviderSyncOperation >();
      
      try
      {
         // Sync RtmList
         ok = RtmListSync.in_computeSync( provider,
                                          serviceImpl,
                                          syncResult,
                                          operations );
         
         Log.i( TAG, "Syncing RtmLists " + ( ok ? "ok" : "failed" ) );
         
         // TODO: Sync locations here.
         
         // Sync RtmTasks
         ok = ok
            && RtmTasksSync.in_computeSync( provider,
                                            serviceImpl,
                                            syncResult,
                                            operations );
         
         Log.i( TAG, "Syncing RtmTasks " + ( ok ? "ok" : "failed" ) );
         
         if ( ok )
         {
            final ArrayList< ContentProviderOperation > batch = new ArrayList< ContentProviderOperation >();
            
            for ( ContentProviderSyncOperation contentProviderSyncOperation : operations )
            {
               final int count = contentProviderSyncOperation.getBatch( batch );
               ContentProviderSyncOperation.updateSyncResult( syncResult,
                                                              contentProviderSyncOperation.getOperationType(),
                                                              count );
            }
            
            provider.applyBatch( batch );
            operations.clear();
         }
      }
      catch ( RemoteException e )
      {
         ++syncResult.stats.numIoExceptions;
         ok = false;
      }
      catch ( OperationApplicationException e )
      {
         syncResult.databaseError = true;
         ok = false;
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
   


   private static void clearSyncResult( SyncResult syncResult )
   {
      syncResult.stats.numInserts = 0;
      syncResult.stats.numUpdates = 0;
      syncResult.stats.numDeletes = 0;
   }
}
