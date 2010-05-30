package dev.drsoran.moloko.sync;

import java.util.Date;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
   private static final String TAG = SyncAdapter.class.getSimpleName();
   
   private final AccountManager accountManager;
   
   private final Context context;
   
   private Date lastUpdated;
   
   

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
      
   }
}
